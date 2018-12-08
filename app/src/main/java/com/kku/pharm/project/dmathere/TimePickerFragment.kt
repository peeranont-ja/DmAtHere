package com.kku.pharm.project.dmathere

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.TimePicker
import com.kku.pharm.project.dmathere.Events.OnTimeSetEvent
import org.greenrobot.eventbus.EventBus
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

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

            EventBus.getDefault().post(OnTimeSetEvent(cal))
        }
        callCount++
    }
}