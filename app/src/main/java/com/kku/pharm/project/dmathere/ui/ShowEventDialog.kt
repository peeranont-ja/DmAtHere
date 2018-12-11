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
import android.widget.Button
import com.kku.pharm.project.dmathere.R
import com.kku.pharm.project.dmathere.data.local.PreferenceHelper
import kotlinx.android.synthetic.main.activity_notification.*
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
        val requestCode = intent.getIntExtra("requestCode", 1)

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

        Log.d("test event time", calendar.time.toString())
        Log.d("test event id", requestCode.toString())

        PreferenceHelper.initPreferenceHelper(this)
        val perfData = PreferenceHelper.alarmTimeInformationList
        val list = perfData?.alarmList

        if (!list.isNullOrEmpty()) {
            val index = list.indices.find { list[it].requestCodeID == requestCode }
            if (index != null) {
                first_medicine_desc.text = concatDescription(list[index].firstMed, list[index].firstMedAmount)

                if (!list[index].secondMed.isNullOrBlank()
                        && !list[index].secondMedAmount.isNullOrBlank()) {
                    second_medicine_title.visibility = View.VISIBLE
                    second_medicine_desc.visibility = View.VISIBLE
                    second_medicine_desc.text = concatDescription(list[index].secondMed!!, list[index].secondMedAmount!!)
                }
            }
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
