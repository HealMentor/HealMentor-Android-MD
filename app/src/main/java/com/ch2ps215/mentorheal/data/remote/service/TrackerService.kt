package com.ch2ps215.mentorheal.data.remote.service

import com.ch2ps215.mentorheal.data.remote.payload.Api
import com.ch2ps215.mentorheal.data.remote.payload.TrackerResponse
import com.ch2ps215.mentorheal.domain.model.Tracker
import retrofit2.Response
import retrofit2.http.*

interface TrackerService {
    @POST("/api/tracker")
    suspend fun save(
        @Body tracker: Tracker
    ): Response<TrackerResponse>

    @GET("/api/tracker")
    suspend fun getTracker(
        @Query("title")
        title: String?,
        @Query("starCount")
        startCount: Int?,
        @Query("description")
        descrription: String?,
    ): Response<Api<List<TrackerResponse>>>
}
