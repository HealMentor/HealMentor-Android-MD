package com.ch2ps215.mentorheal.presentation.twos

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ch2ps215.mentorheal.domain.model.ExpressionDetection
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.domain.usecase.GetExpressionDetectionUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetFormDetectionUseCase
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class DetectionExpressionPagingSource(
    private val getExpressionDetectionUseCase: GetExpressionDetectionUseCase
) : PagingSource<QuerySnapshot, ExpressionDetection>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, ExpressionDetection>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, ExpressionDetection> {
        return try {
            val queryDetection = getExpressionDetectionUseCase().getOrThrow()
            val currentPage = params.key ?: queryDetection.get().await()
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = queryDetection.startAfter(lastVisibleProduct).get().await()

            val data = currentPage.toObjects(ExpressionDetection::class.java)
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