package com.example.eko8757.footballclubfinal.presenter

import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.api.TheSportDBApi
import com.example.eko8757.footballclubfinal.model.PlayerResponse
import com.example.eko8757.footballclubfinal.ui.myactivity.detail_player.DetailViewPlayer
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPresenterPlayer(private val view: DetailViewPlayer, private val apiRepository: ApiRepository, private val gson: Gson) {

    fun getDetailPlayer(idPlayer: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayerDetail(idPlayer)).await(), PlayerResponse::class.java)
            view.getDetailPlayer(data.players.first())
        }
    }
}