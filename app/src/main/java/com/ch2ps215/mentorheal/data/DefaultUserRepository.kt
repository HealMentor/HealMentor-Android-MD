package com.ch2ps215.mentorheal.data

import com.ch2ps215.mentorheal.data.local.UserLocalDataSource
import com.ch2ps215.mentorheal.data.mapper.asEntity
import com.ch2ps215.mentorheal.data.mapper.asModel
import com.ch2ps215.mentorheal.data.remote.UserRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.payload.AuthGoogleRequest
import com.ch2ps215.mentorheal.data.remote.payload.EditUserRequest
import com.ch2ps215.mentorheal.data.remote.payload.SignInRequest
import com.ch2ps215.mentorheal.data.remote.payload.SignUpRequest
import com.ch2ps215.mentorheal.domain.model.User
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultUserRepository(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun signUp(name: String, email: String, password: String): User {
        val req = SignUpRequest(
            name = name,
            email = email,
            password = password
        )
        val res = userRemoteDataSource.signUp(req)
        userLocalDataSource.save(res.asEntity())
        return res.asModel()
    }

    override suspend fun signIn(email: String, password: String): User {
        val req = SignInRequest(
            email = email,
            password = password
        )
        val res = userRemoteDataSource.signIn(req)
        userLocalDataSource.save(res.asEntity())
        return res.asModel()
    }

    override suspend fun authGoogle(idToken: String): User {
        val req = AuthGoogleRequest(idToken = idToken)
        val res = userRemoteDataSource.authGoogle(req)
        userLocalDataSource.save(res.asEntity())
        return res.asModel()
    }

    override suspend fun signOut() {
        userLocalDataSource.delete()
    }

    override fun getUser(): Flow<User?> {
        return userLocalDataSource.getUser().map { it?.asModel() }
    }

    override suspend fun edit(token: String, name: String, password: String, photo: String?): User {
        val req = EditUserRequest(
            name = name,
            password = password,
            photo = photo
        )
        val res = userRemoteDataSource.edit(token, req)
        userLocalDataSource.save(res.asEntity())
        return res.asModel()
    }

    override suspend fun onboarding(isAlreadyOnboarding: Boolean) {
        userLocalDataSource.onboarding(isAlreadyOnboarding)
    }

    override fun onboarding(): Flow<Boolean> {
        return userLocalDataSource.onboarding()
    }

    override suspend fun darkTheme(isDarkTheme: Boolean?) {
        userLocalDataSource.darkTheme(isDarkTheme)
    }

    override fun darkTheme(): Flow<Boolean?> {
        return userLocalDataSource.darkTheme()
    }
}
