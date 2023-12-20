package com.ch2ps215.mentorheal.domain.repository

import com.google.firebase.firestore.Query

interface TrackerRepository {

    suspend fun getTracker(idUser: String): Query

    suspend fun saveTracker(
        title: String,
        starCount: Int,
        description: String,
        feel: String,
        idUser: String
    ): Boolean

}
