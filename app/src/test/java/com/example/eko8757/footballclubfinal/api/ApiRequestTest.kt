package com.example.eko8757.footballclubfinal.api

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRequestTest {
    @Test
fun testSearchEvent() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = TheSportDBApi.getSearchEvent("Chelsea")
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }

    @Test
fun testSearchTeam() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = TheSportDBApi.getSearchTeam("Liverpool")
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}