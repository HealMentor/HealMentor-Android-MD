package com.ch2ps215.mentorheal.data.remote

import com.ch2ps215.mentorheal.data.remote.payload.SaveTrackerRequest
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class TrackerRemoteDataSource(
    private val trackerRef: CollectionReference
) {
    fun getTracker(idUser: String) = trackerRef
        .whereEqualTo("idUser", idUser)
        .limit(10)

    suspend fun saveTracker(req: SaveTrackerRequest, idUser: String): Boolean {
        val id = trackerRef.document().id
        trackerRef.document(id).set(req.copy(id = id, idUser = idUser)).await()
        return true
    }


}
