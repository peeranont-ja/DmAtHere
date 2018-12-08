package com.kku.pharm.project.dmathere

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kku.pharm.project.dmathere.Events.ShowEvent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, arg1: Intent) {
        val i = Intent(context, ShowEvent::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }
}