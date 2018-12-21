package com.kku.pharm.project.dmathere.ui.howToUse

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kku.pharm.project.dmathere.R
import com.kku.pharm.project.dmathere.events.FullScreenPlayerEvent
import com.pierfrancescosoffritti.androidyoutubeplayer.player.playerUtils.FullScreenHelper
import kotlinx.android.synthetic.main.activity_how_to_use.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HowToUseSyringeActivity : AppCompatActivity() {
    private val fullScreenHelper = FullScreenHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_use)

        toolbar.title = "วิธีการฉีดยาโดยใช้กระบอกฉีดยาอินซูลิน"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        attachFragment(
                R.id.layout_contentContainer,
                HowToUseSyringeFragment.newInstance(),
                HowToUseSyringeFragment::class.java.name
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
        if (event.isFullScreen) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            fullScreenHelper.enterFullScreen(this.window.decorView)
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            fullScreenHelper.exitFullScreen(this.window.decorView)
        }
    }
}
