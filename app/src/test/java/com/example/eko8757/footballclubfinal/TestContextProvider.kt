package com.example.eko8757.footballclubfinal

import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider: CoroutineContextProvider() {

    override val main: CoroutineContext = Unconfined
}