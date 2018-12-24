package com.kku.pharm.project.dmathere.ui.alarm

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.kku.pharm.project.dmathere.events.OnMorningAlarmSetEvent
import com.kku.pharm.project.dmathere.R
import com.kku.pharm.project.dmathere.common.BaseDialog.createSimpleOkErrorDialog
import com.kku.pharm.project.dmathere.data.Constant
import com.kku.pharm.project.dmathere.data.Constant.medicineNameList
import com.kku.pharm.project.dmathere.data.local.PreferenceHelper
import com.kku.pharm.project.dmathere.data.model.AlarmTimeInformation
import com.kku.pharm.project.dmathere.utils.AlarmUtils
import com.kku.pharm.project.dmathere.utils.DataFormatter.convertOneDigitToTwoDigit
import kotlinx.android.synthetic.main.fragment_alarm_morning.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE

class AlarmMorningFragment : Fragment() {
    private var alarmUtils = AlarmUtils()
    private var myContext: FragmentActivity? = null

    private var isAddingAction = false
    private var calendar: Calendar? = null
    private var requestCodeID: Int = 0
    private var firstMed: String = ""
    private var firstMedAmount: String = ""
    private var secondMed: String? = null
    private var secondMedAmount: String? = null
    private var isRepeated: Boolean = false
    private val timeDesc = Constant.TIME_DESC_MORNING

    private var hour: Int = 0
    private var minute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceHelper.initPreferenceHelper(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alarm_morning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onAttach(context: Context) {
        myContext = activity as FragmentActivity
        super.onAttach(context)
    }

    private fun setupView() {
        setupSpinner()
        setupExistingData()

        btn_set_alarm.setOnClickListener {
            showTimePickerDialog()
        }

        btn_cancel.setOnClickListener {
            cancelPreviousAlarm()
            PreferenceHelper.alarmTimeMorningInfo = null

            resetData()
            setupSpinner()

            et_first_medicine_amount.setText("")
            et_second_medicine_amount.setText("")

            layout_second_medicine_detail.visibility = View.GONE
            img_second_medicine_action.setImageResource(R.mipmap.ic_add)

            layout_time.visibility = View.GONE
            tv_hour.text = convertOneDigitToTwoDigit(hour)
            tv_minute.text = convertOneDigitToTwoDigit(minute)
            checkBox_repeat_alarm.isChecked = false
            btn_set_alarm.text = "เลือกเวลาใหม่"

            TransitionManager.beginDelayedTransition(layout_button)
            btn_cancel.visibility = View.GONE

            showToast("ยกเลิกเวลาแจ้งเตือนสำเร็จ")
        }

        btn_save.setOnClickListener {
            firstMedAmount = et_first_medicine_amount.text.toString()
            secondMedAmount = if (et_second_medicine_amount.text.isNullOrBlank()) {
                null
            } else {
                et_second_medicine_amount.text.toString()
            }
            setAlarm(calendar)
        }

        img_second_medicine_action.setOnClickListener {
            TransitionManager.beginDelayedTransition(layout_second_medicine)
            if (!isAddingAction) {
                layout_second_medicine_detail.visibility = View.VISIBLE
                img_second_medicine_action.setImageResource(R.mipmap.ic_remove)
                Toast.makeText(context, "เพิ่มยาฉีด ชนิดที่ 2", Toast.LENGTH_SHORT).show()
                isAddingAction = true
            } else {
                layout_second_medicine_detail.visibility = View.GONE
                showToast("ยกเลิกการเพิ่มยาฉีด ชนิดที่ 2")
                img_second_medicine_action.setImageResource(R.mipmap.ic_add)
                isAddingAction = false
            }
        }

        checkBox_repeat_alarm.setOnCheckedChangeListener { buttonView,
                                                           isChecked ->
            isRepeated = isChecked
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun resetData() {
        isAddingAction = false
        calendar = null
        requestCodeID = 0
        firstMed = ""
        firstMedAmount = ""
        secondMed = null
        secondMedAmount = null
        isRepeated = false
        hour = 0
        minute = 0
    }

    private fun setupSpinner() {
        val arrayAdapter = ArrayAdapter(context!!, R.layout.spinner_right_aligned, medicineNameList)
        arrayAdapter.setDropDownViewResource(R.layout.spinner_right_aligned)

        spinner_first_medicine.adapter = arrayAdapter
        spinner_first_medicine.setSelection(0)
        spinner_first_medicine.prompt = "กรุณาเลือกยาฉีดอินซูลิน"
        spinner_first_medicine.gravity = Gravity.CENTER
        spinner_first_medicine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                firstMed = medicineNameList[0]
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                firstMed = medicineNameList[position]
            }
        }

        spinner_second_medicine.adapter = arrayAdapter
        spinner_second_medicine.setSelection(0)
        spinner_second_medicine.prompt = "กรุณาเลือกยาฉีดอินซูลิน"
        spinner_second_medicine.gravity = Gravity.CENTER
        spinner_second_medicine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                if (layout_second_medicine_detail.visibility == View.VISIBLE) {
                    secondMed = medicineNameList[0]
                }
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                secondMed = medicineNameList[position]
            }
        }
    }

    private fun setupExistingData() {
        val previousAlarmData = PreferenceHelper.alarmTimeMorningInfo
        if (previousAlarmData != null) {
            btn_cancel.visibility = View.VISIBLE

            spinner_first_medicine.setSelection(medicineNameList.indexOf(previousAlarmData.firstMed))
            et_first_medicine_amount.setText(previousAlarmData.firstMedAmount)

            calendar = previousAlarmData.calendar
            hour = previousAlarmData.hour
            minute = previousAlarmData.minute

            layout_time.visibility = View.VISIBLE
            tv_hour.text = convertOneDigitToTwoDigit(hour)
            tv_minute.text = convertOneDigitToTwoDigit(minute)

            checkBox_repeat_alarm.visibility = View.VISIBLE
            checkBox_repeat_alarm.isChecked = previousAlarmData.isRepeated
            btn_set_alarm.text = "แก้ไขเวลา"

            if (previousAlarmData.secondMed != null
                    && previousAlarmData.secondMedAmount != null) {

                spinner_second_medicine.setSelection(medicineNameList.indexOf(previousAlarmData.secondMed))
                et_second_medicine_amount.setText(previousAlarmData.secondMedAmount)

                layout_second_medicine_detail.visibility = View.VISIBLE
                img_second_medicine_action.setImageResource(R.mipmap.ic_remove)
                isAddingAction = true

                layout_time.visibility = View.VISIBLE
                checkBox_repeat_alarm.visibility = View.VISIBLE
            }
        }
    }

    private fun setAlarm(calendar: Calendar?) {
        if (calendar != null && !firstMedAmount.isBlank()) {
            val id = (System.currentTimeMillis()/1000/60).toInt()
            alarmUtils.setAlarm(
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
        val currentAlarmInfo = AlarmTimeInformation(
                requestCodeID,
                firstMed,
                firstMedAmount,
                secondMed,
                secondMedAmount,
                calendar!!.get(HOUR_OF_DAY),
                calendar!!.get(MINUTE),
                calendar!!,
                timeDesc,
                isRepeated,
                Constant.STATUS_ACTIVE
        )

        cancelPreviousAlarm()
        PreferenceHelper.alarmTimeMorningInfo = currentAlarmInfo

        TransitionManager.beginDelayedTransition(layout_button)
        btn_cancel.visibility = View.VISIBLE

        val currentMorningAlarmData = PreferenceHelper.alarmTimeMorningInfo
        if (currentMorningAlarmData != null) {
            Log.d("test nowMorningAlarm", currentMorningAlarmData.requestCodeID.toString())
            Log.d("test nowMorningAlarm", currentMorningAlarmData.firstMed)
            Log.d("test nowMorningAlarm", currentMorningAlarmData.firstMedAmount)
            Log.d("test nowMorningAlarm", currentMorningAlarmData.secondMed.toString())
            Log.d("test nowMorningAlarm", currentMorningAlarmData.secondMedAmount.toString())
            Log.d("test nowMorningAlarm", currentMorningAlarmData.hour.toString())
            Log.d("test nowMorningAlarm", currentMorningAlarmData.minute.toString())
            Log.d("test nowMorningAlarm", currentMorningAlarmData.calendar.time.toString())
            Log.d("test nowMorningAlarm", currentMorningAlarmData.timeDescription)
            Log.d("test nowMorningAlarm", currentMorningAlarmData.isRepeated.toString())
            Log.d("test nowMorningAlarm", currentMorningAlarmData.status)
        }
    }

    private fun cancelPreviousAlarm() {
        val previousAlarmData = PreferenceHelper.alarmTimeMorningInfo
        if (previousAlarmData != null) {
            alarmUtils.cancelAlarm(context!!, previousAlarmData.requestCodeID)
            Log.d("test cancel previous ", "Cancel success.")
        }
    }


    private fun showTimePickerDialog() {
        val newFragment = TimePickerFragment()
        newFragment.timeDescription = timeDesc
        newFragment.show(myContext!!.supportFragmentManager, newFragment.tag)
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
    fun onOnTimeSetEvent(event: OnMorningAlarmSetEvent) {
        Log.d("test event", "Morning Event")
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