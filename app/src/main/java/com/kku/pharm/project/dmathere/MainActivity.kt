package com.kku.pharm.project.dmathere

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
    }

    private fun setupView(){
        btn_alarm.setOnClickListener {
            goToAlarmPage()
        }
    }

    private fun goToAlarmPage(){
        val intent = Intent(this, AlarmActivity::class.java)
        startActivity(intent)
    }
}
