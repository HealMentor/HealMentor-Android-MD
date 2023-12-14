package com.ch2ps215.mentorheal.presentation.twos

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.domain.usecase.GetFormDetectionUseCase
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class DetectionPagingSource(
    private val getFormDetectionUseCase: GetFormDetectionUseCase
) : PagingSource<QuerySnapshot, FormDetection>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, FormDetection>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, FormDetection> {
        return try {
            val queryDetection = getFormDetectionUseCase().getOrThrow()
            val currentPage = params.key ?: queryDetection.get().await()
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = queryDetection.startAfter(lastVisibleProduct).get().await()

            val data = currentPage.toObjects(FormDetection::class.java)
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