package com.example.eko8757.footballclubfinal.ui.myactivity.detail_event

import com.example.eko8757.footballclubfinal.model.DetailEvent
import com.example.eko8757.footballclubfinal.model.Team

interface DetailViewEvent {
    fun showTeam(data: Team)
    fun getDetailEvent(data: DetailEvent)
}