package com.ch2ps215.mentorheal.data.remote

import com.ch2ps215.mentorheal.data.remote.payload.DeleteArticleLikesRequest
import com.ch2ps215.mentorheal.data.remote.payload.SaveArticleLikesRequest
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class ArticleRemoteDataSource(
    private val articlesRef: CollectionReference,
    private val articlesLikesRef: CollectionReference,
) {
    fun getArticles() = articlesRef.limit(20)
    fun getArticleById(id: String) = articlesRef.document(id)
    fun getArticles(category: String) = articlesRef
        .whereEqualTo("category", category)
        .limit(20)

    suspend fun getFavoriteArticles(idUser: String): Query {
        var ids: List<String> = emptyList()
        articlesLikesRef.whereEqualTo("userId", idUser).get().await().documents.forEach {
            ids = ids.plus(it["id"].toString())
        }
        return articlesRef.whereIn("id", ids)
    }

    fun isArticleFavorite(idArticle: String, idUser: String) =
        articlesLikesRef.document("${idArticle}_${idUser}")

    fun like(req: SaveArticleLikesRequest) {
        articlesLikesRef.document("${req.id}_${req.userId}").apply { set(req) }
    }

    fun unLike(req: DeleteArticleLikesRequest) {
        articlesLikesRef.document("${req.id}_${req.userId}").delete()
    }

}
