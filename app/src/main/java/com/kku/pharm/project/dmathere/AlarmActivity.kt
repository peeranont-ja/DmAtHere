package com.kku.pharm.project.dmathere

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.Toolbar
import android.support.v4.app.FragmentPagerAdapter
import com.kku.pharm.project.dmathere.R.id.toolbar
import kotlinx.android.synthetic.main.activity_alarm.*


class AlarmActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)


        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        setupViewPager(viewpager)

        tabs.setupWithViewPager(viewpager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AlarmMorningFragment(), "เช้า")
        adapter.addFragment(AlarmNoonFragment(), "กลางวัน")
        adapter.addFragment(AlarmEveningFragment(), "เย็น")
        adapter.addFragment(AlarmNightFragment(), "ก่อนนอน")
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

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }
}
