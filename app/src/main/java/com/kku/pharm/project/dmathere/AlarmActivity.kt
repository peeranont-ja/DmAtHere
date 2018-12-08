package com.kku.pharm.project.dmathere

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_alarm.*


class AlarmActivity : AppCompatActivity() {

    private val iconList = intArrayOf(
            R.drawable.ic_sunrise,
            R.drawable.ic_sun,
            R.drawable.ic_sunset,
            R.drawable.ic_night)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        setupViewPager(viewpager)


        tabs.setupWithViewPager(viewpager)
        setupTabIcons()
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
        private val mFragmentList:ArrayList<Fragment> = ArrayList()
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
//            return mFragmentTitleList[position]
            return null
        }
    }

}
