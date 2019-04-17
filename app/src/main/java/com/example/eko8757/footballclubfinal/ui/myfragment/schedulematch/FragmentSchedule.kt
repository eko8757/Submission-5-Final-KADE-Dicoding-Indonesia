package com.example.eko8757.footballclubfinal.ui.myfragment.schedulematch

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.adapter.AdapterTabPager

class FragmentSchedule : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_schedule, container, false)
        viewPager = view.findViewById(R.id.viewPager_schedule)
        tabLayout = view.findViewById(R.id.tab_schedule)

        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
        return view
    }

    private fun setupViewPager(pager: ViewPager) {
        val adapter = fragmentManager?.let {
            AdapterTabPager(it)
        }
        val next = FragmentNext.nextIntance()
        adapter?.addFragment(next, "NEXT EVENT")
        val prev = FragmentPrev.prevInstance()
        adapter?.addFragment(prev, "PREV EVENT")
        pager?.adapter = adapter
    }


    companion object {
        fun scheduleInstance(): FragmentSchedule = FragmentSchedule()
    }
}
