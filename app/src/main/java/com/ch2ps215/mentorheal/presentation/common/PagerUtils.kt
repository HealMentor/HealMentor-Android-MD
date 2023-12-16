package com.ch2ps215.mentorheal.presentation.common

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot


object PagerUtils {
    fun <V : Any> createFirestorePager(
        clazz: Class<V>,
        pageSize: Int = 10,
        enablePlaceholders: Boolean = false,
        block: suspend () -> Query
    ): Pager<QuerySnapshot, V> = Pager(
        pagingSourceFactory = { BaseFirestorePagingSource(clazz, block) },
        config = PagingConfig(enablePlaceholders = enablePlaceholders, pageSize = pageSize)
    )
}