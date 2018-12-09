package com.kku.pharm.project.dmathere

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_alarm.*
import java.util.*


class AlarmActivity : AppCompatActivity() {
    private val iconList = intArrayOf(
            R.drawable.ic_sunrise,
            R.drawable.ic_sun,
            R.drawable.ic_sunset,
            R.drawable.ic_night)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        toolbar.title = "ตั้งค่าแจ้งเตือนเวลาฉีดอินซูลิน"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        setupViewPager(viewpager)

        tabs.setupWithViewPager(viewpager)
        setupTabIcons()

        updateTabTextColors()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupTabIcons() {
        tabs.getTabAt(0)!!.setIcon(iconList[0])
        tabs.getTabAt(1)!!.setIcon(iconList[1])
        tabs.getTabAt(2)!!.setIcon(iconList[2])
        tabs.getTabAt(3)!!.setIcon(iconList[3])
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(AlarmMorningFragment(), "เช้า")
        adapter.addFrag(AlarmNoonFragment(), "กลางวัน")
        adapter.addFrag(AlarmEveningFragment(), "เย็น")
        adapter.addFrag(AlarmNightFragment(), "ก่อนนอน")
        viewPager.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList: ArrayList<Fragment> = ArrayList()
        private val mFragmentTitleList: ArrayList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
//            return null
        }
    }

    private fun updateTabTextColors() {
        val tabsContainer = tabs.getChildAt(0) as LinearLayout
//        for (i in 0 until tabs.tabCount) {
//            val item = tabsContainer.getChildAt(i) as LinearLayout
//            val tv = item.getChildAt(1) as TextView
//            tv.setTextColor(if (i%2 == 0) Color.MAGENTA else Color.BLUE)
//
//            val tabsContainer = tabs.getChildAt(0) as LinearLayout
//            val childLayout1 = tabsContainer.getChildAt(2) as LinearLayout
//            val childLayout2 = tabsContainer.getChildAt(3) as LinearLayout
//
//            var tabView = childLayout1.getChildAt(0).parent as LinearLayout
//            tabView.setBackgroundColor(Color.GREEN)
//
//            tabView = childLayout2.getChildAt(0).parent as LinearLayout
//            tabView.setBackgroundColor(Color.RED)
//        }


//        val childLayout1 = tabsContainer.getChildAt(0)as LinearLayout
//        val childLayout2 = tabsContainer.getChildAt(1)as LinearLayout
//        val childLayout3 = tabsContainer.getChildAt(2)as LinearLayout
//        val childLayout4 = tabsContainer.getChildAt(3)as LinearLayout
//
//        var tabView = childLayout1.getChildAt(0).parent as LinearLayout
//        tabView.setBackgroundColor(ContextCompat.getColor(this, R.color.oldGold))
////        tabView.elevation = -10F
    }

//    public override fun onStart() {
//        super.onStart()
//        EventBus.getDefault().register(this)
//    }
//
//    public override fun onStop() {
//        super.onStop()
//        EventBus.getDefault().unregister(this)
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onSetAlarmTimeEvent(event: SetAlarmTimeEvent) {
//        /* Do something */
//        showTimePickerDialog()
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onOnTimeSetEvent(event: OnTimeSetEvent) {
//        /* Do something */
//        setAlarm(event.calendar)
//    }

    private fun showTimePickerDialog() {
        val newFragment = TimePickerFragment()
        newFragment.show(this.supportFragmentManager, newFragment.tag)
    }



}
