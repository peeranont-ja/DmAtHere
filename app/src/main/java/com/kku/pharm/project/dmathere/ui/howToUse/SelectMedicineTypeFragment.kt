package com.kku.pharm.project.dmathere.ui.howToUse

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kku.pharm.project.dmathere.R
import kotlinx.android.synthetic.main.fragment_select_medicine_type.*

class SelectMedicineTypeFragment : Fragment() {
    companion object {
        fun newInstance(): SelectMedicineTypeFragment {
            val fragment = SelectMedicineTypeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_select_medicine_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        btn_pen_fill.setOnClickListener {
            goToHowToUsePenFillActivity()
        }
        btn_syringe.setOnClickListener {
            goToHowToUseSyringeActivity()
        }
    }

    private fun goToHowToUsePenFillActivity() {
        val intent = Intent(context!!, HowToUsePenfillActivity::class.java)
        startActivity(intent)
    }

    private fun goToHowToUseSyringeActivity() {
        val intent = Intent(context!!, HowToUseSyringeActivity::class.java)
        startActivity(intent)
    }
}