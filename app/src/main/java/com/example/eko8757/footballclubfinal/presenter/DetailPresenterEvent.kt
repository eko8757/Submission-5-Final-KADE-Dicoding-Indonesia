package com.example.eko8757.footballclubfinal.presenter

import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.api.TheSportDBApi
import com.example.eko8757.footballclubfinal.model.DetailResponseEvent
import com.example.eko8757.footballclubfinal.model.TeamResponse
import com.example.eko8757.footballclubfinal.ui.myactivity.detail_event.DetailViewEvent
import com.example.eko8757.footballclubfinal.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPresenterEvent(private val view: DetailViewEvent,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeam(idTeam: String) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(idTeam)), TeamResponse::class.java)
            }
            view.showTeam(data.await().teams.first())
        }
    }

    fun getDetailEvent(idEvent: String) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getDetailEvent(idEvent)), DetailResponseEvent::class.java)
            }

            view.getDetailEvent(data.await().events.first())
        }
    }
}