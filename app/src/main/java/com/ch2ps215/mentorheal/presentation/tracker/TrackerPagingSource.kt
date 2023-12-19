package com.ch2ps215.mentorheal.presentation.tracker

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.domain.model.Tracker
import com.ch2ps215.mentorheal.domain.usecase.GetFormDetectionUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetTrackerUseCase
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class TrackerPagingSource(
    private val getTrackerUseCase: GetTrackerUseCase
) : PagingSource<QuerySnapshot, Tracker>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, Tracker>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Tracker> {
        return try {
            val queryDetection = getTrackerUseCase().getOrThrow()
            val currentPage = params.key ?: queryDetection.get().await()
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = queryDetection.startAfter(lastVisibleProduct).get().await()

            val data = currentPage.toObjects(Tracker::class.java)
            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}