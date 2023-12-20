package com.ch2ps215.mentorheal.data.remote

import androidx.core.net.toUri
import com.ch2ps215.mentorheal.data.remote.payload.FormDetectionResponse
import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionExpressionRequest
import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionFormRequest
import com.ch2ps215.mentorheal.data.remote.payload.SaveTrackerRequest
import com.ch2ps215.mentorheal.data.remote.service.DetectionService
import com.ch2ps215.mentorheal.data.remote.service.FormService
import com.ch2ps215.mentorheal.data.remote.service.TrackerService
import com.ch2ps215.mentorheal.domain.model.ExpressionDetection
import com.ch2ps215.mentorheal.domain.model.Form
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Named

class TrackerRemoteDataSource (
    @Named("trackerRef") private val trackerRef: CollectionReference,
    private val trackerService: TrackerService
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
