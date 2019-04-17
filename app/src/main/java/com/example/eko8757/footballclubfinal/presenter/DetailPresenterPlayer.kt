package com.example.eko8757.footballclubfinal.presenter

import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.api.TheSportDBApi
import com.example.eko8757.footballclubfinal.model.PlayerResponse
import com.example.eko8757.footballclubfinal.ui.myactivity.detail_player.DetailViewPlayer
import com.example.eko8757.footballclubfinal.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPresenterPlayer(private val view: DetailViewPlayer,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailPlayer(idPlayer: String) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayerDetail(idPlayer)), PlayerResponse::class.java)
            }
            view.getDetailPlayer(data.await().players.first())
        }
    }
}