package com.kku.pharm.project.dmathere.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.navigation.NavigationView
import com.kku.pharm.project.dmathere.R
import com.kku.pharm.project.dmathere.data.Constant
import com.kku.pharm.project.dmathere.ui.about.AboutActivity
import com.kku.pharm.project.dmathere.ui.alarm.AlarmActivity
import com.kku.pharm.project.dmathere.ui.howToUse.SelectMedicineTypeActivity
import com.kku.pharm.project.dmathere.ui.medicine.MedicineListActivity
import com.kku.pharm.project.dmathere.ui.sideEffect.SideEffectActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        setupView()

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.itemIconTintList = null
        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun setupView() {
        Glide.with(this)
                .load(R.drawable.bg)
                .into(img_bg)

        Glide.with(this)
                .load(R.drawable.ic_app_logo)
                .apply(RequestOptions().fitCenter())
                .into(img_logo)

        btn_alarm.setOnClickListener {
            goToAlarmPage()
        }

        btn_medicine_type.setOnClickListener {
            goToMedicineListPage()
        }

        btn_side_effect.setOnClickListener {
            goToSideEffectPage()
        }

        btn_how_to.setOnClickListener {
            goToSelectMedicineTypePage()
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.DISABLE_KEYGUARD)
                != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.INTERNET,
                            android.Manifest.permission.ACCESS_NETWORK_STATE,
                            android.Manifest.permission.READ_PHONE_STATE,
                            android.Manifest.permission.ACCESS_WIFI_STATE),
                    Constant.PERMISSIONS_REQUEST_CODE)
        } else {
            Log.e("PERMISSION CHECK", "PERMISSION GRANTED")
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_alarm -> {
                goToAlarmPage()
            }
            R.id.nav_drugs -> {
                goToMedicineListPage()
            }
            R.id.nav_how_to -> {
                goToSelectMedicineTypePage()
            }
            R.id.nav_side_effect -> {
                goToSideEffectPage()
            }
            R.id.nav_about -> {
                goToAboutPage()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun goToAlarmPage() {
        val intent = Intent(this, AlarmActivity::class.java)
        startActivity(intent)
    }

    private fun goToMedicineListPage() {
        val intent = Intent(this, MedicineListActivity::class.java)
        startActivity(intent)
    }

    private fun goToSideEffectPage() {
        val intent = Intent(this, SideEffectActivity::class.java)
        startActivity(intent)
    }

    private fun goToSelectMedicineTypePage() {
        val intent = Intent(this, SelectMedicineTypeActivity::class.java)
        startActivity(intent)
    }

    private fun goToAboutPage() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }
}
