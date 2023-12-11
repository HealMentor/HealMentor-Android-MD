package com.ch2ps215.mentorheal.data.remote.service

import com.ch2ps215.mentorheal.data.remote.payload.DetectionResponse
import com.ch2ps215.mentorheal.domain.model.Form
import retrofit2.Response
import retrofit2.http.*

interface DetectionService {
    @POST("/api/detection/detect")
    @Multipart
    suspend fun detect(
        @Query("form")
        form: Form,
    ): Response<DetectionResponse>

}
