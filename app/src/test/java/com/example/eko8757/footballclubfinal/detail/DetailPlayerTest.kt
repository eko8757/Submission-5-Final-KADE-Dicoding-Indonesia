package com.example.eko8757.footballclubfinal.detail

import com.example.eko8757.footballclubfinal.TestContextProvider
import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.api.TheSportDBApi
import com.example.eko8757.footballclubfinal.model.Player
import com.example.eko8757.footballclubfinal.model.PlayerResponse
import com.example.eko8757.footballclubfinal.presenter.DetailPresenterPlayer
import com.example.eko8757.footballclubfinal.ui.myactivity.detail_player.DetailViewPlayer
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPlayerTest {

    @Mock
    private lateinit var view: DetailViewPlayer

    @Mock
    private lateinit var gson: Gson

    @Mock
    lateinit var apiRequest: ApiRepository
    private lateinit var presenter: DetailPresenterPlayer

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenterPlayer(view, apiRequest, gson, TestContextProvider())
    }

    @Test
fun getTestDetailPlayers() {
        val idPlayer = "34145411"
        val detailPlayer = listOf<Player>(Player(idPlayer))
        val playerResponse = PlayerResponse(detailPlayer, detailPlayer)
        Mockito. `when` (gson.fromJson(apiRequest.doRequest(TheSportDBApi.getPlayerDetail(idPlayer)),
                PlayerResponse::class.java)).thenReturn(playerResponse)
        presenter.getDetailPlayer(idPlayer)
        Mockito.verify(view).getDetailPlayer(detailPlayer.first())
    }
}