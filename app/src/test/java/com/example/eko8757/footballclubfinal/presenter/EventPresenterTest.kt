package com.example.eko8757.footballclubfinal.presenter

import com.example.eko8757.footballclubfinal.TestContextProvider
import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.api.TheSportDBApi
import com.example.eko8757.footballclubfinal.model.Events
import com.example.eko8757.footballclubfinal.model.EventsResponse
import com.example.eko8757.footballclubfinal.ui.myfragment.schedulematch.MatchView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class EventPresenterTest {

    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchPresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testPrevEventsList() {

        val event = listOf<Events>(Events("576548"))
        val response = EventsResponse(event, event)

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPrevEvents("576548")),
                EventsResponse::class.java
        )).thenReturn(response)

        presenter.getPrevEventsList("576548")

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPrevEvents(event)
        Mockito.verify(view).hideLoading()

    }

    @Test
    fun testAPIDorequest(){
        val url = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=133600"
        apiRepository.doRequest(TheSportDBApi.getTeamDetail("133600"))
        verify(apiRepository).doRequest(url)
    }
}