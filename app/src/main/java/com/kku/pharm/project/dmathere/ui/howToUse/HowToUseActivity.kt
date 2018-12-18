package com.kku.pharm.project.dmathere.ui.howToUse

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kku.pharm.project.dmathere.R
import com.kku.pharm.project.dmathere.events.FullScreenPlayerEvent
import kotlinx.android.synthetic.main.activity_how_to_use.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HowToUseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_use)

        toolbar.title = "วิธีฉีดอินซูลิน"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        attachFragment(
                R.id.layout_contentContainer,
                HowToUseFragment.newInstance(),
                HowToUseFragment::class.java.name
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

        public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSetAlarmTimeEvent(event: FullScreenPlayerEvent) {
        requestedOrientation = if (event.isFullScreen){
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }else{
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}
