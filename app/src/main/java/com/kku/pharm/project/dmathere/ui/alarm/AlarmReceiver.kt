package com.kku.pharm.project.dmathere.ui.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, arg1: Intent) {
        val requestCode = arg1.getIntExtra("requestCode", 1)

        val i = Intent(context, ShowEventDialog::class.java)
        i.putExtra("requestCode", requestCode)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }
}