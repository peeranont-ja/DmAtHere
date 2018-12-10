package com.kku.pharm.project.dmathere

import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import java.util.*

internal class ShowEventDialog : Activity(), View.OnClickListener {

    private lateinit var pm: PowerManager
    private lateinit var wl: PowerManager.WakeLock
    private lateinit var km: KeyguardManager
    private lateinit var kl: KeyguardManager.KeyguardLock
    private lateinit var r: Ringtone

    private lateinit var btnStop: Button
    @SuppressLint("InvalidWakeLockTag")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        Log.i("ShowEventDialog", "onCreate() in DismissLock")
        pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        km = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        kl = km.newKeyguardLock("ShowEventDialog")
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE, "ShowEventDialog")
        wl.acquire(10 * 60 * 1000L /*10 minutes*/) //wake up the screen
        kl.disableKeyguard()

        setContentView(R.layout.activity_notification)

        btnStop = findViewById(R.id.btnStop)
        btnStop.setOnClickListener(this)

        val calendar = Calendar.getInstance()
        val dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val alarmID = "$dayOfTheWeek$day$month$year$hour$minute"
        Log.d("test event time", calendar.time.toString())
        Log.d("test event id", alarmID)

    }

    override fun onClick(v: View) {
        if (v.id == R.id.btnStop) {
//            AlarmActivity.listValue.removeAt(0)
            this.finish()
        }
    }

    override fun onResume() {

        super.onResume()
        wl.acquire()//must call this!
        var notif: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        if (notif == null) {
            notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            if (notif == null) {
                notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            }
        }
        r = RingtoneManager.getRingtone(applicationContext, notif)
        r.play()


    }

    public override fun onPause() {
        super.onPause()
        wl.release()
        if (r.isPlaying) {
            r.stop()
        }
    }
}
