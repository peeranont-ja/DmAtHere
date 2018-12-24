package com.kku.pharm.project.dmathere.ui.alarm

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.kku.pharm.project.dmathere.data.Constant
import com.kku.pharm.project.dmathere.events.OnAfternoonAlarmSetEvent
import com.kku.pharm.project.dmathere.events.OnEveningAlarmSetEvent
import com.kku.pharm.project.dmathere.events.OnMorningAlarmSetEvent
import com.kku.pharm.project.dmathere.events.OnNightAlarmSetEvent
import com.kku.pharm.project.dmathere.ui.medicine.MedicineDetailFragment
import org.greenrobot.eventbus.EventBus
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    var timeDescription = ""
    private var callCount = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute,
                DateFormat.is24HourFormat(activity))

    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        if (callCount == 0) {
            // Do something with the time chosen by the user
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            cal.set(Calendar.SECOND, 0)

            Log.d("test time desc", timeDescription)
            when (timeDescription) {
                Constant.TIME_DESC_MORNING -> EventBus.getDefault().post(OnMorningAlarmSetEvent(cal))
                Constant.TIME_DESC_AFTERNOON -> EventBus.getDefault().post(OnAfternoonAlarmSetEvent(cal))
                Constant.TIME_DESC_EVENING -> EventBus.getDefault().post(OnEveningAlarmSetEvent(cal))
                Constant.TIME_DESC_NIGHT -> EventBus.getDefault().post(OnNightAlarmSetEvent(cal))
            }
        }
        callCount++
    }
}