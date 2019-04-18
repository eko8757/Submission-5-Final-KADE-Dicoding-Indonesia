package com.example.eko8757.footballclubfinal.presenter

import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.api.TheSportDBApi
import com.example.eko8757.footballclubfinal.model.DetailResponseEvent
import com.example.eko8757.footballclubfinal.model.TeamResponse
import com.example.eko8757.footballclubfinal.ui.myactivity.detail_event.DetailViewEvent
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPresenterEvent(private val view: DetailViewEvent, private val apiRepository: ApiRepository, private val gson: Gson) {

    fun getTeam(idTeam: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(idTeam)).await(), TeamResponse::class.java)
            view.showTeam(data.teams.first())
        }
    }

    fun getDetailEvent(idEvent: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getDetailEvent(idEvent)).await(), DetailResponseEvent::class.java)
            view.getDetailEvent(data.events.first())
        }
    }
}