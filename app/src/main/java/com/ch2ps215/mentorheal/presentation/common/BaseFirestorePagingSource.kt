package com.ch2ps215.mentorheal.presentation.common

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class BaseFirestorePagingSource<V : Any>(
    private val clazz: Class<V>,
    private val block: suspend () -> Query
) : PagingSource<QuerySnapshot, V>() {
    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, V> {

        return try {
            val query = block()
            val currentPage = params.key ?: query.get().await()
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = query.startAfter(lastVisibleProduct).get().await()

            val data = currentPage.toObjects(clazz)

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, V>): QuerySnapshot? {
        return null
    }
}