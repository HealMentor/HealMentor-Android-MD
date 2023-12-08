package com.ch2ps215.data.remote

import com.ch2ps215.data.remote.payload.AuthGoogleRequest
import com.ch2ps215.data.remote.payload.EditUserRequest
import com.ch2ps215.data.remote.payload.SignInRequest
import com.ch2ps215.data.remote.payload.SignUpRequest
import com.ch2ps215.data.remote.payload.UserResponse
import com.ch2ps215.data.remote.service.UserService
import okhttp3.ResponseBody
import org.json.JSONObject

class UserRemoteDataSource(
    private val userService: UserService
) {

    suspend fun signUp(req: SignUpRequest): UserResponse {
        val res = userService.signUp(req)
        val data = res.takeIf { it.isSuccessful }?.body()
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun signIn(req: SignInRequest): UserResponse {
        val res = userService.signIn(req)
        val data = res.takeIf { it.isSuccessful }?.body()
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun authGoogle(req: AuthGoogleRequest): UserResponse {
        val res = userService.authGoogle(req)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun edit(token: String, req: EditUserRequest): UserResponse {
        val res = userService.edit("Bearer $token", req)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }
}

fun ResponseBody.getErrorMessage(key: String = "data"): String {
    return JSONObject(charStream().readText()).getString(key)
}
