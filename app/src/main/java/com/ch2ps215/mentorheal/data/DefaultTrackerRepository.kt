package com.ch2ps215.mentorheal.data

import com.ch2ps215.mentorheal.data.local.TrackerLocalDataSource
import com.ch2ps215.mentorheal.data.remote.TrackerRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.payload.SaveTrackerRequest
import com.ch2ps215.mentorheal.domain.repository.TrackerRepository
import com.google.firebase.firestore.Query

class DefaultTrackerRepository(
    private val trackerLocalDataSource: TrackerLocalDataSource,
    private val trackerRemoteDataSource: TrackerRemoteDataSource,
) : TrackerRepository {
    override suspend fun getTracker(idUser: String): Query {
        return trackerRemoteDataSource.getTracker(idUser)
    }

    override suspend fun saveTracker(
        title: String,
        starCount: Int,
        description: String,
        idUser: String
    ): Boolean {
        val req = SaveTrackerRequest(
            id = "",
            title = title,
            starCount = starCount,
            description = description,
            idUser = idUser
        )

        return trackerRemoteDataSource.saveTracker(req, idUser)
    }

}
