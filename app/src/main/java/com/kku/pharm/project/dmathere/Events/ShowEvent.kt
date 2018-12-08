package com.kku.pharm.project.dmathere.Events

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
import com.kku.pharm.project.dmathere.AlarmActivity
import com.kku.pharm.project.dmathere.AlarmMorningFragment
import com.kku.pharm.project.dmathere.R

internal class ShowEvent : Activity(), View.OnClickListener {

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
        Log.i("ShowEvent", "onCreate() in DismissLock")
        pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        km = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        kl = km.newKeyguardLock("ShowEvent")
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE, "ShowEvent")
        wl.acquire() //wake up the screen
        kl.disableKeyguard()

        setContentView(R.layout.activity_notification)

        btnStop = findViewById(R.id.btnStop)
        btnStop.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        if (v.id == R.id.btnStop) {
            AlarmActivity.listValue.removeAt(0)
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
