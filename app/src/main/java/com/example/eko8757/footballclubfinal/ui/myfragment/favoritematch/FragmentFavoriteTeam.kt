package com.example.eko8757.footballclubfinal.ui.myfragment.favoritematch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.adapter.AdapterFavoriteTeamList
import com.example.eko8757.footballclubfinal.db.DBTeam
import com.example.eko8757.footballclubfinal.db.database
import com.example.eko8757.footballclubfinal.ui.myactivity.detail_team.DetailActivityTeam
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class FragmentFavoriteTeam : Fragment() {

    private var favorites: MutableList<DBTeam> = mutableListOf()
    private lateinit var favoriteList: RecyclerView
    private lateinit var adapter: AdapterFavoriteTeamList

    private fun showFavorite() {
        context?.database?.use {
            val result = select(DBTeam.TABLE_TEAMS)
            val favorite = result.parseList(classParser<DBTeam>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favorite_team, container, false)
        favoriteList = view.find(R.id.recycler_favoriteTeam) as RecyclerView
        favoriteList.layoutManager = LinearLayoutManager(activity)
        adapter = AdapterFavoriteTeamList(favorites) {
            activity?.startActivity<DetailActivityTeam>("idTeam" to "${it.teamId}")
        }
        favoriteList.adapter = adapter
        showFavorite()
        return view
    }

    companion object {
        fun teamIntance(): FragmentFavoriteTeam = FragmentFavoriteTeam()
    }
}
