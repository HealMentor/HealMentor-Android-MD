package com.ch2ps215.mentorheal.domain.repository

import com.ch2ps215.mentorheal.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun signUp(name: String, email: String, password: String): User

    suspend fun signIn(email: String, password: String): User

    suspend fun authGoogle(idToken: String): User

    suspend fun signOut()

    fun getUser(): Flow<User?>

    suspend fun edit(token: String, name: String, password: String, photo: String?): User

    suspend fun onboarding(isAlreadyOnboarding: Boolean)

    fun onboarding(): Flow<Boolean>

    suspend fun darkTheme(isDarkTheme: Boolean?)

    fun darkTheme(): Flow<Boolean?>
}
