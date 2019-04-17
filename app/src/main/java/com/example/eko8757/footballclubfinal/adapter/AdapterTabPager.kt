package com.example.eko8757.footballclubfinal.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter


class AdapterTabPager(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments = ArrayList<Fragment>()
    private val titles = ArrayList<String>()

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments.get(position)

    override fun getPageTitle(position: Int): CharSequence? {
        return titles.get(position)
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }
}