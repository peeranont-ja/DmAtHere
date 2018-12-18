package com.kku.pharm.project.dmathere.ui.sideEffect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kku.pharm.project.dmathere.R
import kotlinx.android.synthetic.main.activity_side_effect.*

class SideEffectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_effect)

        toolbar.title = "อาการข้างเคียงของยาฉีดอินซูลิน"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        attachFragment(
                R.id.layout_contentContainer,
                SideEffectFragment.newInstance(),
                SideEffectFragment::class.java.name
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
