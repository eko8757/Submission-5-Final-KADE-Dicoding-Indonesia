package com.example.eko8757.footballclubfinal.ui.myfragment.schedulematch

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import android.widget.TextView

import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.adapter.AdapterTabPager

class FragmentSchedule : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    @SuppressLint("ResourceAsColor")
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
        adapter?.addFragment(next, "Next Event")
        val prev = FragmentPrev.prevInstance()
        adapter?.addFragment(prev, "Prev Event")
        pager?.adapter = adapter
    }


    companion object {
        fun scheduleInstance(): FragmentSchedule = FragmentSchedule()
    }
}
