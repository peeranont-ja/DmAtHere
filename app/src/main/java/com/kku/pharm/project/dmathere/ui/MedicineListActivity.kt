package com.kku.pharm.project.dmathere.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kku.pharm.project.dmathere.R
import kotlinx.android.synthetic.main.activity_alarm.*

class MedicineListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_list)

        toolbar.title = "ยาฉีดอินซูลิน"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
