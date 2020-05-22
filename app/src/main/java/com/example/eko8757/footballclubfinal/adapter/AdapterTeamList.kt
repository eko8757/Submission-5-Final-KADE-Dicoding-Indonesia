package com.example.eko8757.footballclubfinal.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.model.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team_list.view.*

class AdapterTeamList(private var items: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<AdapterTeamList.TeamListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TeamListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_team_list, parent, false))

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: TeamListViewHolder, position: Int) {
        holder.bindTeamList(items[position], listener)
    }

    class TeamListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindTeamList(items: Team, listener: (Team) -> Unit) {
            itemView.name_club.text = items.teamName
            Picasso.get().load(items.teamBadge)
                    .into(itemView.image_club)
            itemView.setOnClickListener {
                listener(items)
            }
        }
    }
}