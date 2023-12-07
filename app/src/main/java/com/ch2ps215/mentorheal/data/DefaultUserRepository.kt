package com.ch2ps215.mentorheal.data

import com.ch2ps215.mentorheal.domain.model.User
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class DefaultUserRepository() : UserRepository {

    override suspend fun signUp(name: String, email: String, password: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun authGoogle(idToken: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")    }

    override fun getUser(): Flow<User?> {
        TODO("Not yet implemented")
    }

    override suspend fun edit(token: String, name: String, password: String, photo: String?): User {
        TODO("Not yet implemented")
    }

    override suspend fun onboarding(isAlreadyOnboarding: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onboarding(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun darkTheme(isDarkTheme: Boolean?) {
        TODO("Not yet implemented")
    }

    override fun darkTheme(): Flow<Boolean?> {
        TODO("Not yet implemented")
    }
}
