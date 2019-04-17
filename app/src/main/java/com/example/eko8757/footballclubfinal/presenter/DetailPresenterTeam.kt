package com.example.eko8757.footballclubfinal.presenter

import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.api.TheSportDBApi
import com.example.eko8757.footballclubfinal.model.PlayerResponse
import com.example.eko8757.footballclubfinal.model.TeamResponse
import com.example.eko8757.footballclubfinal.ui.myactivity.detail_team.DetailViewTeam
import com.example.eko8757.footballclubfinal.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPresenterTeam(private val view: DetailViewTeam,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider =
                                  CoroutineContextProvider()) {

    fun getDetailTeam(idTeam: String?) {

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(idTeam)),
                        TeamResponse::class.java)
            }
            view.getDetailTeam(data.await().teams.first())
        }
    }

    fun getPlayerTeam(idTeam: String){
        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayerTeam(idTeam)),
                        PlayerResponse::class.java)
            }
            view.showPlayer(data.await().player)
        }
    }
}