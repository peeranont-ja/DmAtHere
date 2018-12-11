package com.kku.pharm.project.dmathere.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.kku.pharm.project.dmathere.ui.AlarmReceiver
import java.util.*


object AlarmUtils {

    fun setAlarm(context: Context,
                 requestCode: Int,
                 calendar: Calendar,
                 isRepeated: Boolean) {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("requestCode", requestCode)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (isRepeated) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent)
            Log.d("test alarm", calendar.time.toString() + ": Set Repeat Alarm success.")
        } else {
            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent)
            Log.d("test alarm", calendar.time.toString() + ": Set Alarm success.")
        }
    }

    fun cancelAlarm(context: Context,
                    requestCode: Int) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        Log.d("test alarm",": Cancel Alarm success.")
    }
}