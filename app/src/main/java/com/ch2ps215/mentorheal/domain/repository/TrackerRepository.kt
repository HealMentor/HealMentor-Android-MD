package com.ch2ps215.mentorheal.domain.repository

import com.ch2ps215.mentorheal.data.local.TrackerLocalDataSource
import com.ch2ps215.mentorheal.data.local.entity.TrackerEntity
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import java.io.File

interface TrackerRepository {

    suspend fun getTracker(idUser: String): Query

    suspend fun saveTracker( title: String, starCount: Int, description: String,feel: String,  idUser: String): Boolean

}
