package com.ch2ps215.mentorheal.di

import com.ch2ps215.mentorheal.BuildConfig
import com.ch2ps215.mentorheal.core.Constants.ARTICLE
import com.ch2ps215.mentorheal.core.Constants.DETECTIONS
import com.ch2ps215.mentorheal.data.remote.ArticleRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.DetectionRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.UserRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.service.ArticleService
import com.ch2ps215.mentorheal.data.remote.service.DetectionService
import com.ch2ps215.mentorheal.data.remote.service.UserService
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        }

        val callFactory = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://identitytoolkit.googleapis.com/")
            .callFactory(callFactory)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(retrofit: Retrofit): UserRemoteDataSource {
        val userService = retrofit.create<UserService>()
        return UserRemoteDataSource(userService)
    }


    @Provides
    @Singleton
    @Named("detectionsRef")
    fun provideDetectionsRef(): CollectionReference {
        return Firebase.firestore.collection(DETECTIONS)
    }

    @Provides
    @Singleton
    @Named("articlesRef")
    fun provideArticlesRef(): CollectionReference {
        return Firebase.firestore.collection(ARTICLE)
    }

    @Provides
    @Singleton
    fun provideArticleRemoteDataSource(
        @Named("articlesRef") firebaseFirestore: CollectionReference,
        retrofit: Retrofit
    ): ArticleRemoteDataSource {
        val detectionService = retrofit.create<ArticleService>()
        return ArticleRemoteDataSource(detectionService)
    }

    @Provides
    @Singleton
    fun provideDetectionRemoteDataSource(
        @Named("detectionsRef") firebaseFirestore: CollectionReference,
        retrofit: Retrofit
    ): DetectionRemoteDataSource {
        val detectionService = retrofit.create<DetectionService>()
        return DetectionRemoteDataSource(firebaseFirestore, detectionService)
    }
}
