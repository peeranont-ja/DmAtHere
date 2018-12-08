package com.kku.pharm.project.dmathere

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_alarm_morning.*
import com.isapanah.awesomespinner.AwesomeSpinner




class AlarmMorningFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alarm_morning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        setupMedicineTypeSpinner()
    }

    private fun setupMedicineTypeSpinner(){
        val categories =  arrayOf(
                "NovoRapid Penfill",
                "Insulatard Penfill",
                "Actrapid Penfill",
                "Mixtard Penfill",
                "NovoMix",
                "Lantus Solostar",
                "Levemir Flexpen",
                "Mixtard 30 HM",
                "Actrapid HM",
                "Insulatard HM"
                )

        val adapter = ArrayAdapter(context,android.R.layout.simple_spinner_item, categories)
//        val categoriesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)

        spinner_first_medicine.setAdapter(adapter)
        spinner_second_medicine.setAdapter(adapter)

        spinner_first_medicine.setOnSpinnerItemClickListener { position, itemAtPosition ->
            //TODO YOUR ACTIONS
        }

        spinner_second_medicine.setOnSpinnerItemClickListener { position, itemAtPosition ->
            //TODO YOUR ACTIONS
        }
    }

}