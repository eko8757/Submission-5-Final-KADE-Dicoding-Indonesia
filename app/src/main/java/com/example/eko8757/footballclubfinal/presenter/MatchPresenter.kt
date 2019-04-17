package com.example.eko8757.footballclubfinal.presenter

import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.api.TheSportDBApi
import com.example.eko8757.footballclubfinal.model.EventsResponse
import com.example.eko8757.footballclubfinal.ui.myfragment.schedulematch.MatchView
import com.example.eko8757.footballclubfinal.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPrevEventsList(idLeague: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPrevEvents(idLeague)), EventsResponse::class.java)
            }
            view.showPrevEvents(data.await().events)
            view.hideLoading()
        }
    }

    fun getNextEventsList(idLeague: String){
        view.showLoading()
        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextEvents(idLeague)), EventsResponse::class.java)
            }
            view.showNextEvents(data.await().events)
            view.hideLoading()
        }
    }

    fun getSearchEventList(match: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getSearchEvent(match)), EventsResponse::class.java)
            }
            view.showNextEvents(data.await().searchEvent)
            view.hideLoading()
        }
    }
}