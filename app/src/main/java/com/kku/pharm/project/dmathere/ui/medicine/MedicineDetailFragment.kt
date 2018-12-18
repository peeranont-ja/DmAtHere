package com.kku.pharm.project.dmathere.ui.medicine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kku.pharm.project.dmathere.R
import com.kku.pharm.project.dmathere.data.Constant
import com.kku.pharm.project.dmathere.ui.sideEffect.SideEffectActivity
import kotlinx.android.synthetic.main.fragment_medicine_detail.*

class MedicineDetailFragment : Fragment() {
    private var index = 0

    companion object {
        fun newInstance(index: Int): MedicineDetailFragment {
            val fragment = MedicineDetailFragment()
            val args = Bundle()
            args.putInt("index", index)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_medicine_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = arguments!!.getInt("index")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        img_medicine.setImageResource(Constant.imgArray[index])
        tv_medicine_type_desc.text = Constant.medicineDataList[index].medicineType
        tv_medicine_usage_desc.text = Constant.medicineDataList[index].usage
        tv_medicine_pre_use_desc.text = Constant.medicineDataList[index].drugStoragePreUsing
        tv_medicine_post_use_desc.text = Constant.medicineDataList[index].drugStoragePostUsing

        if (Constant.medicineDataList[index].isPenfill) {
            tv_post_using_title.text = "ระหว่างใช้งาน (อินซูลินอยู่ในปากกา) :"
        } else {
            tv_post_using_title.text = "ระหว่างใช้งาน :"
        }

        btn_side_effect.setOnClickListener {
            goToSideEffectPage()
        }
    }

    private fun goToSideEffectPage() {
        val intent = Intent(context, SideEffectActivity::class.java)
        startActivity(intent)
    }
}