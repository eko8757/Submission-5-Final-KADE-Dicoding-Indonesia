package com.example.eko8757.footballclubfinal.ui.myfragment.favoritematch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.adapter.AdapterFavoriteEventList
import com.example.eko8757.footballclubfinal.db.DBEvent
import com.example.eko8757.footballclubfinal.db.database
import com.example.eko8757.footballclubfinal.ui.myactivity.detail_event.DetailActivityEvent
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity


class FragmentFavoriteMatch : Fragment() {

    private var favorites: MutableList<DBEvent> = mutableListOf()
    private lateinit var favoriteList: RecyclerView
    private lateinit var adapter: AdapterFavoriteEventList

    private fun showFavorite() {
        context?.database?.use {
            val result = select(DBEvent.TABLE_EVENT)
            val favorite = result.parseList(classParser<DBEvent>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_match, container, false)
        favoriteList = view.find(R.id.recycler_favoriteMatch) as RecyclerView

        favoriteList.layoutManager = LinearLayoutManager(activity)
        adapter = AdapterFavoriteEventList(favorites) {
            activity?.startActivity<DetailActivityEvent>("idEvent" to "${it.eventId}")
        }
        favoriteList.adapter = adapter
        showFavorite()
        return view
    }

    companion object {
        fun matchIntance(): FragmentFavoriteMatch = FragmentFavoriteMatch()
    }
}
