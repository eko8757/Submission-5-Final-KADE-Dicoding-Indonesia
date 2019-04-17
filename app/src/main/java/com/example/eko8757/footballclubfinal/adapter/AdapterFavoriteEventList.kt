package com.example.eko8757.footballclubfinal.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.db.DBEvent
import kotlinx.android.synthetic.main.item_list.view.*

class AdapterFavoriteEventList(private val favorite: List<DBEvent>, private val listener: (DBEvent) -> Unit)
    : RecyclerView.Adapter<AdapterFavoriteEventList.FavoriteEventViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        return FavoriteEventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteEventViewHolder, position: Int) {
        holder.bindFavorite(favorite[position], listener)
    }

    class FavoriteEventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindFavorite(favorite: DBEvent, listener: (DBEvent) -> Unit) {
            itemView.date.text = favorite.dateEvent
            itemView.club_home.text = favorite.homeTeam
            itemView.score_home.text = favorite.homeScore
            itemView.score_away.text = favorite.awayScore
            itemView.club_away.text = favorite.awayTeam
            itemView.vs.text = "VS"
            itemView.setOnClickListener {
                listener(favorite)
            }
        }

    }
}