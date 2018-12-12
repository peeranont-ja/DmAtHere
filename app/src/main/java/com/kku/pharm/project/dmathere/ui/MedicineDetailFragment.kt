package com.kku.pharm.project.dmathere.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kku.pharm.project.dmathere.R
import com.kku.pharm.project.dmathere.data.Constant
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
    }

}