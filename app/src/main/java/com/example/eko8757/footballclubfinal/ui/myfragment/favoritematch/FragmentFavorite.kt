package com.example.eko8757.footballclubfinal.ui.myfragment.favoritematch

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.adapter.AdapterTabPager


class FragmentFavorite : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    companion object {
        fun favoriteInstance(): FragmentFavorite = FragmentFavorite()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_favorite, container, false)
        viewPager = view.findViewById(R.id.viewPager_favorite)
        tabLayout = view.findViewById(R.id.tab_favorite)

        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
        return view
    }

    private fun setupViewPager(pager: ViewPager) {
        val adapter =  fragmentManager?.let { AdapterTabPager(it) }
        val match = FragmentFavoriteMatch.matchIntance()
        adapter?.addFragment(match, "MATCHES")
        val team = FragmentFavoriteTeam.teamIntance()
        adapter?.addFragment(team, "TEAMS")
        pager.adapter = adapter
    }
}
