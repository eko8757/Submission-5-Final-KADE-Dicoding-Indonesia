package com.example.eko8757.footballclubfinal.presenter

import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.api.TheSportDBApi
import com.example.eko8757.footballclubfinal.model.EventsResponse
import com.example.eko8757.footballclubfinal.ui.myfragment.schedulematch.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter(private val view: MatchView, private val apiRepository: ApiRepository, private val gson: Gson) {

    fun getPrevEventsList(idLeague: String?) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPrevEvents(idLeague)).await(), EventsResponse::class.java)
            view.showPrevEvents(data.events)
            view.hideLoading()
        }
    }

    fun getNextEventsList(idLeague: String) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextEvents(idLeague)).await(), EventsResponse::class.java)
            view.showNextEvents(data.events)
            view.hideLoading()
        }
    }

    fun getSearchEventList(match: String?) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getSearchEvent(match)).await(), EventsResponse::class.java)
            view.showNextEvents(data.searchEvent)
            view.hideLoading()
        }
    }
}