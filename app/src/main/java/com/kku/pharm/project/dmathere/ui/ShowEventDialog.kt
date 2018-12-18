package com.kku.pharm.project.dmathere.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.kku.pharm.project.dmathere.R
import com.kku.pharm.project.dmathere.data.Constant
import com.kku.pharm.project.dmathere.data.local.PreferenceHelper
import kotlinx.android.synthetic.main.activity_notification.*
import java.util.*

internal class ShowEventDialog : Activity(), View.OnClickListener {
    private lateinit var pm: PowerManager
    private lateinit var wl: PowerManager.WakeLock
    private lateinit var km: KeyguardManager
    private lateinit var kl: KeyguardManager.KeyguardLock
    private lateinit var r: Ringtone

    @SuppressLint("InvalidWakeLockTag")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        val requestCode = intent.getIntExtra("requestCode", 1)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        Log.i("ShowEventDialog", "onCreate() in DismissLock")
        pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        km = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        kl = km.newKeyguardLock("ShowEventDialog")
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                or PowerManager.ACQUIRE_CAUSES_WAKEUP
                or PowerManager.ON_AFTER_RELEASE, "ShowEventDialog")
        wl.acquire(10 * 60 * 1000L /*10 minutes*/) //wake up the screen
        kl.disableKeyguard()

        setContentView(R.layout.activity_notification)

        btn_stop.setOnClickListener(this)

        val calendar = Calendar.getInstance()

        Log.d("test event time", calendar.time.toString())
        Log.d("test event id", requestCode.toString())

        PreferenceHelper.initPreferenceHelper(this)

        val morningAlarmData = PreferenceHelper.alarmTimeMorningInfo
        val afternoonAlarmData = PreferenceHelper.alarmTimeAfternoonInfo
        val eveningAlarmData = PreferenceHelper.alarmTimeEveningInfo
        val nightAlarmData = PreferenceHelper.alarmTimeNightInfo


        val alarmData = when (requestCode) {
            morningAlarmData?.requestCodeID -> morningAlarmData
            afternoonAlarmData?.requestCodeID -> afternoonAlarmData
            eveningAlarmData?.requestCodeID -> eveningAlarmData
            nightAlarmData?.requestCodeID -> nightAlarmData
            else -> null
        }

        if (alarmData != null) {
            tv_first_medicine_desc.text = concatDescription(alarmData.firstMed, alarmData.firstMedAmount)

            if (!alarmData.secondMed.isNullOrBlank()
                    && !alarmData.secondMedAmount.isNullOrBlank()) {
                tv_second_medicine_title.visibility = View.VISIBLE
                tv_second_medicine_desc.visibility = View.VISIBLE
                tv_second_medicine_desc.text = concatDescription(alarmData.secondMed!!, alarmData.secondMedAmount!!)
            }
        } else {
            layout_alarm_info.visibility = View.GONE
            tv_default_desc.visibility = View.VISIBLE
        }
    }

    private fun concatDescription(medicine: String, amount: String): SpannableString {
        val concatWording = "$medicine \nปริมาณ $amount ยูนิต"
        val wordToSpan = SpannableString(concatWording)
        wordToSpan.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
                0,
                medicine.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        wordToSpan.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
                medicine.length + 8,
                concatWording.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return wordToSpan
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_stop) {
            this.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        wl.acquire()//must call this!
        var notification: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        if (notification == null) {
            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            if (notification == null) {
                notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            }
        }
        r = RingtoneManager.getRingtone(applicationContext, notification)
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
