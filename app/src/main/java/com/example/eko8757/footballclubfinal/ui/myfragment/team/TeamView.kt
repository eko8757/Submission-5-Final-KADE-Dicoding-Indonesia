package com.example.eko8757.footballclubfinal.ui.myfragment.team

import com.example.eko8757.footballclubfinal.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}