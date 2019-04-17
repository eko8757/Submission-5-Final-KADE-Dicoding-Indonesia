package com.example.eko8757.footballclubfinal.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.db.DBTeam
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team_list.view.*

class AdapterFavoriteTeamList(private val favorite: List<DBTeam>, private val listener: (DBTeam) -> Unit)
    : RecyclerView.Adapter<AdapterFavoriteTeamList.FavoriteTeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamViewHolder {
        return FavoriteTeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_team_list, parent, false))
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteTeamViewHolder, position: Int) {
        holder.bindFavoriteTeam(favorite[position], listener)
    }

    class FavoriteTeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindFavoriteTeam(favorite: DBTeam, listener: (DBTeam) -> Unit) {
            itemView.name_club.text = favorite.teamName
            Picasso.get().load(favorite.badgeTeam).into(itemView.image_club)
            itemView.setOnClickListener {
                listener(favorite)
            }
        }
    }
}