package com.example.eko8757.footballclubfinal.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.model.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_player_list.view.*

class AdapterPlayerList(private var items: List<Player>, val listener: (Player) -> Unit)
    : RecyclerView.Adapter<AdapterPlayerList.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder =
            PlayerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_player_list, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindPlayer(items[position], listener)
    }

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindPlayer(items: Player, listener: (Player) -> Unit) {
            Picasso.get().load(items.strCutout).into(itemView.image_player_team)
            itemView.name_player_team.text = items.strPlayer
            itemView.position_player_team.text = items.strPosition
            itemView.setOnClickListener {
                listener(items)
            }
        }

    }
}