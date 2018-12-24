package com.kku.pharm.project.dmathere.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.kku.pharm.project.dmathere.data.local.PreferenceHelper
import com.kku.pharm.project.dmathere.ui.alarm.AlarmReceiver
import java.util.*


class AlarmUtils {

    fun setAlarm(context: Context,
                 requestCode: Int,
                 calendar: Calendar,
                 isRepeated: Boolean) {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("requestCode", requestCode)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
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
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        Log.d("test alarm", ": Cancel Alarm success.")
    }

    fun cancelAllAlarm(context: Context) {
        PreferenceHelper.initPreferenceHelper(context)
        val morningData = PreferenceHelper.alarmTimeMorningInfo
        val afternoonData = PreferenceHelper.alarmTimeAfternoonInfo
        val eveningData = PreferenceHelper.alarmTimeEveningInfo
        val nightData = PreferenceHelper.alarmTimeNightInfo

        if (morningData != null) {
            cancelAlarm(context, morningData.requestCodeID)
        }

        if (afternoonData != null) {
            cancelAlarm(context, afternoonData.requestCodeID)
        }

        if (eveningData != null) {
            cancelAlarm(context, eveningData.requestCodeID)
        }

        if (nightData != null) {
            cancelAlarm(context, nightData.requestCodeID)
        }
    }
}