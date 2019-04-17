package com.example.eko8757.footballclubfinal.model

import android.view.SearchEvent
import com.google.gson.annotations.SerializedName

data class EventsResponse(
        val events: List<Events>,

        @SerializedName("event")
        val searchEvent: List<Events>
)