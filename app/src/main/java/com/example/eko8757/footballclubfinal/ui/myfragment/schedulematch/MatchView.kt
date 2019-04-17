package com.example.eko8757.footballclubfinal.ui.myfragment.schedulematch

import com.example.eko8757.footballclubfinal.model.Events

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showPrevEvents(data: List<Events>)
    fun showNextEvents(data: List<Events>)
}