package com.ch2ps215.mentorheal.data.remote.service

import com.ch2ps215.mentorheal.data.remote.payload.DetectFormRequest
import com.ch2ps215.mentorheal.data.remote.payload.FormDetectionResponse
import com.ch2ps215.mentorheal.domain.model.Form
import retrofit2.Response
import retrofit2.http.*

interface FormService {
    @POST("/prediction")
    suspend fun detect(
        @Body form: DetectFormRequest
    ): Response<FormDetectionResponse>

}
