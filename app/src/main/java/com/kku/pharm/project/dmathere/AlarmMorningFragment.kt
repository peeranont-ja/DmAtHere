package com.kku.pharm.project.dmathere

import android.os.Bundle
import android.support.v4.app.Fragment
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.kku.pharm.project.dmathere.Events.SetAlarmTimeEvent
import kotlinx.android.synthetic.main.fragment_alarm_morning.*
import org.greenrobot.eventbus.EventBus


class AlarmMorningFragment : Fragment() {
    private var isAddingAction = false

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
        btn_set_alarm.setOnClickListener {
            EventBus.getDefault().post(SetAlarmTimeEvent())
        }

        img_second_medicine_action.setOnClickListener {
//            TransitionManager.beginDelayedTransition(layout_second_medicine)
            if(!isAddingAction) {
                layout_second_medicine_detail.visibility = View.VISIBLE
                img_second_medicine_action.setImageResource(R.mipmap.ic_remove)
                isAddingAction = true
            }else{
                layout_second_medicine_detail.visibility = View.GONE
                img_second_medicine_action.setImageResource(R.mipmap.ic_add)
                isAddingAction = false
            }
        }
    }

    private fun setupMedicineTypeSpinner() {
        val categories = arrayOf(
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

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, categories)

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