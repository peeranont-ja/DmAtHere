package com.kku.pharm.project.dmathere

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.kku.pharm.project.dmathere.Events.OnTimeSetEvent
import kotlinx.android.synthetic.main.fragment_alarm_morning.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import java.util.Calendar.*


class AlarmMorningFragment : Fragment() {
    private var isAddingAction = false
    private lateinit var calendar: Calendar
    private var myContext: FragmentActivity? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alarm_morning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onAttach(context: Context?) {
        myContext = activity as FragmentActivity
        super.onAttach(context)
    }

    private fun setupView() {
        setupMedicineTypeSpinner()
        btn_set_alarm.setOnClickListener {
            showTimePickerDialog()
        }

        btn_save.setOnClickListener {
            setAlarm(calendar)
        }

        img_second_medicine_action.setOnClickListener {
            TransitionManager.beginDelayedTransition(layout_second_medicine)
            if (!isAddingAction) {
                layout_second_medicine_detail.visibility = View.VISIBLE
                img_second_medicine_action.setImageResource(R.mipmap.ic_remove)
                isAddingAction = true
            } else {
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

        val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, categories)

        spinner_first_medicine.setAdapter(adapter)
        spinner_second_medicine.setAdapter(adapter)

        spinner_first_medicine.setOnSpinnerItemClickListener { position, itemAtPosition ->
            //TODO YOUR ACTIONS
        }

        spinner_second_medicine.setOnSpinnerItemClickListener { position, itemAtPosition ->
            //TODO YOUR ACTIONS
        }
    }

    private fun setAlarm(targetCal: Calendar) {
        val id = System.currentTimeMillis().toInt()
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0)
        val alarmManager = activity!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.timeInMillis, pendingIntent)
        Log.d("test alarm", targetCal.time.toString() + ": Set Alarm success.")

    }

    private fun showTimePickerDialog() {
        val newFragment = TimePickerFragment()
        newFragment.show(myContext?.supportFragmentManager, newFragment.tag)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onOnTimeSetEvent(event: OnTimeSetEvent) {
        calendar = event.calendar
        calendar.set(SECOND, 0)
        Log.d("test time", event.calendar.time.toString())

        TransitionManager.beginDelayedTransition(layout_alarm_time)
        layout_time.visibility = View.VISIBLE

        val hour = calendar.get(HOUR_OF_DAY).toString()
//        val amPM = calendar.get(AM_PM)

        tv_hour.text = if (hour.length == 1) {
            "0${calendar.get(HOUR_OF_DAY)}"
        } else calendar.get(HOUR_OF_DAY).toString()

        tv_minute.text = calendar.get(MINUTE).toString()

//        tv_am_pm.text = if (amPM == 0) {
//            "AM"
//        } else "PM"

        btn_set_alarm.text = if (tv_hour.visibility == View.VISIBLE) {
            "แก้ไขเวลา"
        } else "ตั้งค่าเวลาใหม่"
    }
}