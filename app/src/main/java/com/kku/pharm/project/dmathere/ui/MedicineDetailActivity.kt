package com.kku.pharm.project.dmathere.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.kku.pharm.project.dmathere.R
import com.kku.pharm.project.dmathere.data.Constant
import kotlinx.android.synthetic.main.activity_medicine_detail.*

class MedicineDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_detail)

        val index = intent.getIntExtra("index", 0)
        val medicineName = Constant.medicineNameList[index]

        toolbar_title.text = medicineName
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        attachFragment(
                R.id.layout_contentContainer,
                MedicineDetailFragment.newInstance(index),
                MedicineDetailFragment::class.java.name
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun attachFragment(containerLayout: Int, fragment: Fragment, tag: String) {
        val currentFragment = supportFragmentManager
                .findFragmentByTag(tag)

        if (currentFragment == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(
                            containerLayout,
                            fragment,
                            tag
                    )
                    .commit()
        }
    }
}