package com.example.eko8757.footballclubfinal.presenter

import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.api.TheSportDBApi
import com.example.eko8757.footballclubfinal.model.PlayerResponse
import com.example.eko8757.footballclubfinal.model.TeamResponse
import com.example.eko8757.footballclubfinal.ui.myactivity.detail_team.DetailViewTeam
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPresenterTeam(private val view: DetailViewTeam, private val apiRepository: ApiRepository, private val gson: Gson) {

    fun getDetailTeam(idTeam: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(idTeam)).await(), TeamResponse::class.java)
            view.getDetailTeam(data.teams.first())
        }
    }

    fun getPlayerTeam(idTeam: String){
        GlobalScope.launch(Dispatchers.Main){
            val data= gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayerTeam(idTeam)).await(), PlayerResponse::class.java)
            view.showPlayer(data.player)
        }
    }
}