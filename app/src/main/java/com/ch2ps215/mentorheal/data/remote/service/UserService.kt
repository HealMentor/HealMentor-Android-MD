package com.ch2ps215.mentorheal.data.remote.service

import com.ch2ps215.mentorheal.data.remote.payload.Api
import com.ch2ps215.mentorheal.data.remote.payload.AuthGoogleRequest
import com.ch2ps215.mentorheal.data.remote.payload.EditUserRequest
import com.ch2ps215.mentorheal.data.remote.payload.SignInRequest
import com.ch2ps215.mentorheal.data.remote.payload.SignUpRequest
import com.ch2ps215.mentorheal.data.remote.payload.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {

    @POST("v1/accounts:signUp?key=AIzaSyArfPRd9mjvE0-Yv1Gt-JYtLaYbPongprw")
    suspend fun signUp(
        @Body req: SignUpRequest
    ): Response<UserResponse>

    @POST("v1/accounts:signInWithPassword?key=AIzaSyArfPRd9mjvE0-Yv1Gt-JYtLaYbPongprw")
    suspend fun signIn(
        @Body req: SignInRequest
    ): Response<UserResponse>

    @POST("api/user/auth-google")
    suspend fun authGoogle(
        @Body req: AuthGoogleRequest
    ): Response<Api<UserResponse>>

    @PUT("/api/user/edit")
    suspend fun edit(
        @Header("Authorization") token: String,
        @Body req: EditUserRequest
    ): Response<Api<UserResponse>>
}
