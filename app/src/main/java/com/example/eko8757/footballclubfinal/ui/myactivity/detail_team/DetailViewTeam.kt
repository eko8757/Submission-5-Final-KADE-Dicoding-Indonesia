package com.example.eko8757.footballclubfinal.ui.myactivity.detail_team

import com.example.eko8757.footballclubfinal.model.Player
import com.example.eko8757.footballclubfinal.model.Team

interface DetailViewTeam {
    fun getDetailTeam(data: Team)
    fun showPlayer(data: List<Player>)
}