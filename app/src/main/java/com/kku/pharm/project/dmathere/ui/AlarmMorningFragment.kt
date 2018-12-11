package com.kku.pharm.project.dmathere.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.kku.pharm.project.dmathere.Events.OnTimeSetEvent
import com.kku.pharm.project.dmathere.R
import com.kku.pharm.project.dmathere.common.BaseDialog.createSimpleOkErrorDialog
import com.kku.pharm.project.dmathere.data.Constant
import com.kku.pharm.project.dmathere.data.local.PreferenceHelper
import com.kku.pharm.project.dmathere.data.model.AlarmTimeInformation
import com.kku.pharm.project.dmathere.data.model.AlarmTimeInformationList
import com.kku.pharm.project.dmathere.utils.AlarmUtils
import kotlinx.android.synthetic.main.fragment_alarm_morning.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import java.util.Calendar.*
import kotlin.collections.ArrayList

class AlarmMorningFragment : Fragment() {
    private var isAddingAction = false
    private var calendar: Calendar? = null
    private var myContext: FragmentActivity? = null

    private var requestCodeID: Int = 0
    private var firstMed: String = ""
    private var firstMedAmount: String = ""
    private var secondMed: String? = null
    private var secondMedAmount: String? = null
    private var isRepeated: Boolean = false

    private var hour: Int = 0
    private var minute: Int = 0

    private val categories = arrayOf(
            "NovoRapid® Penfill®",
            "Insulatard® Penfill®",
            "Actrapid® Penfill®",
            "Mixtard® Penfill®",
            "NovoMix®",
            "Lantus® Solostar®",
            "Levemir® Flexpen",
            "Mixtard® 30 HM",
            "Actrapid® HM",
            "Insulatard® HM"
    )

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
        btn_set_alarm.setOnClickListener {
            showTimePickerDialog()
        }

        btn_save.setOnClickListener {
            firstMedAmount = et_first_medicine_amount.text.toString()

            secondMedAmount = if (et_second_medicine_amount.text.isNullOrBlank()) {
                null
            } else et_second_medicine_amount.text.toString()

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

        checkBox_repeat_alarm.setOnCheckedChangeListener { buttonView,
                                                           isChecked ->
            isRepeated = isChecked
        }

        setupSpinner()
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun setupSpinner() {
        val arrayAdapter = ArrayAdapter(context!!, R.layout.spinner_right_aligned, categories)
        arrayAdapter.setDropDownViewResource(R.layout.spinner_right_aligned)

        spinner_first_medicine.adapter = arrayAdapter
        spinner_first_medicine.setSelection(0)
        spinner_first_medicine.prompt = "กรุณาเลือกยาฉีดอินซูลิน"
        spinner_first_medicine.gravity = Gravity.CENTER

        spinner_second_medicine.adapter = arrayAdapter
        spinner_second_medicine.setSelection(0)
        spinner_second_medicine.prompt = "กรุณาเลือกยาฉีดอินซูลิน"
        spinner_second_medicine.gravity = Gravity.CENTER

        spinner_first_medicine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                firstMed = categories[0]
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                firstMed = categories[position]
            }
        }

        spinner_second_medicine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                if (layout_second_medicine_detail.visibility == View.VISIBLE) {
                    secondMed = categories[0]
                }
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                secondMed = categories[position]
            }
        }
    }

    private fun setAlarm(calendar: Calendar?) {
        if (calendar != null && !firstMedAmount.isBlank()) {
            val id = System.currentTimeMillis().toInt()
            AlarmUtils.setAlarm(
                    context!!,
                    id,
                    calendar,
                    isRepeated
            )
            requestCodeID = id
            saveAlarmData()
            showToast("ตั้งเวลาแจ้งเตือนสำเร็จ")
        } else {
            if (firstMedAmount.isBlank()) {
                createSimpleOkErrorDialog(
                        context!!,
                        "กรุณากรอกปริมาณยาฉีดอินซูลิน ชนิดที่1 ก่อนกดบันทึก")
                        .show()
            } else if (layout_second_medicine_detail.visibility == View.VISIBLE
                    && secondMedAmount.isNullOrBlank()) {
                createSimpleOkErrorDialog(
                        context!!,
                        "กรุณากรอกปริมาณยาฉีดอินซูลิน ชนิดที่2 ก่อนกดบันทึก")
                        .show()
            } else if (calendar == null) {
                createSimpleOkErrorDialog(
                        context!!,
                        "กรุณาเลือกเวลาแจ้งเตือนก่อนกดบันทึก")
                        .show()
            }
        }
    }

    private fun saveAlarmData() {
        val alarmInfo = AlarmTimeInformation(
                requestCodeID,
                firstMed,
                firstMedAmount,
                secondMed,
                secondMedAmount,
                calendar!!.get(HOUR_OF_DAY),
                calendar!!.get(MINUTE),
                Constant.TIME_DESC_MORNING,
                false,
                Constant.STATUS_ACTIVE
        )

        PreferenceHelper.initPreferenceHelper(context!!)

        val perfData = PreferenceHelper.alarmTimeInformationList
        var list: ArrayList<AlarmTimeInformation> = ArrayList()
        if (perfData != null) {
            list = perfData.alarmList
            list.add(alarmInfo)
        } else {
            list.add(alarmInfo)
        }
        PreferenceHelper.alarmTimeInformationList = AlarmTimeInformationList(list)

        val alarmList = PreferenceHelper.alarmTimeInformationList
        for (i in 0 until alarmList?.alarmList!!.size) {
            Log.d("test perfs $i", alarmList.alarmList[i].requestCodeID.toString())
            Log.d("test perfs $i", alarmList.alarmList[i].firstMed)
            Log.d("test perfs $i", alarmList.alarmList[i].firstMedAmount)
            Log.d("test perfs $i", alarmList.alarmList[i].secondMed.toString())
            Log.d("test perfs $i", alarmList.alarmList[i].secondMedAmount.toString())
            Log.d("test perfs $i", alarmList.alarmList[i].hour.toString())
            Log.d("test perfs $i", alarmList.alarmList[i].minute.toString())
            Log.d("test perfs $i", alarmList.alarmList[i].timeDescription)
            Log.d("test perfs $i", alarmList.alarmList[i].isRepeated.toString())
            Log.d("test perfs $i", alarmList.alarmList[i].status)
        }
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
        hour = calendar!!.get(HOUR_OF_DAY)
        minute = calendar!!.get(MINUTE)
        Log.d("test time", event.calendar.time.toString())

        TransitionManager.beginDelayedTransition(layout_alarm_time)
        layout_time.visibility = View.VISIBLE
        checkBox_repeat_alarm.visibility = View.VISIBLE

        tv_hour.text = if (hour < 10 || hour.toString().length == 1) {
            "0$hour"
        } else hour.toString()

        tv_minute.text = if (minute < 10 || minute.toString().length == 1) {
            "0$minute"
        } else minute.toString()

        btn_set_alarm.text = if (tv_hour.visibility == View.VISIBLE) {
            "แก้ไขเวลา"
        } else "เลือกเวลาใหม่"
    }
}