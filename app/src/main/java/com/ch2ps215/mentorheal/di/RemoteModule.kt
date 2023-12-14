package com.ch2ps215.mentorheal.di

import android.content.Context
import com.ch2ps215.mentorheal.BuildConfig
import com.ch2ps215.mentorheal.core.Constants.ARTICLE
import com.ch2ps215.mentorheal.core.Constants.DETECTIONS
import com.ch2ps215.mentorheal.core.Constants.DETECTIONS_EXPRESSION
import com.ch2ps215.mentorheal.data.remote.ArticleRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.DetectionRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.TfLiteUserClassifierDataSource
import com.ch2ps215.mentorheal.data.remote.UserRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.service.ArticleService
import com.ch2ps215.mentorheal.data.remote.service.DetectionService
import com.ch2ps215.mentorheal.data.remote.service.FormService
import com.ch2ps215.mentorheal.data.remote.service.UserService
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @Named("retrofitFirestore")
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
    @Named("retrofitForm")
    fun providePredictionRetrofit(): Retrofit {
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
            .baseUrl("https://healmentor-model-api-reuadfy3ia-et.a.run.app")
            .callFactory(callFactory)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        @Named("retrofitFirestore") retrofitFirestore: Retrofit,
    ): UserRemoteDataSource {
        val userService = retrofitFirestore.create<UserService>()
        return UserRemoteDataSource(userService)
    }


    @Provides
    @Singleton
    @Named("detectionsFormRef")
    fun provideDetectionsFormRef(): CollectionReference {
        return Firebase.firestore.collection(DETECTIONS)
    }

    @Provides
    @Singleton
    @Named("detectionsExpressionRef")
    fun provideDetectionsExpressionRef(): CollectionReference {
        return Firebase.firestore.collection(DETECTIONS_EXPRESSION)
    }

    @Provides
    @Singleton
    @Named("articlesRef")
    fun provideArticlesRef(): CollectionReference {
        return Firebase.firestore.collection(ARTICLE)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideArticleRemoteDataSource(
        @Named("articlesRef") firebaseFirestore: CollectionReference,
        @Named("retrofitFirestore") retrofitFirestore: Retrofit,
    ): ArticleRemoteDataSource {
        val detectionService = retrofitFirestore.create<ArticleService>()
        return ArticleRemoteDataSource(detectionService)
    }

    @Provides
    @Singleton
    fun provideDetectionRemoteDataSource(
        @Named("detectionsFormRef") detectionsFormRef: CollectionReference,
        @Named("detectionsExpressionRef") detectionsExpressionRef: CollectionReference,
        firebaseStorage: FirebaseStorage,
        @Named("retrofitFirestore") retrofitFirestore: Retrofit,
        @Named("retrofitForm") retrofitForm: Retrofit
    ): DetectionRemoteDataSource {
        val detectionService = retrofitFirestore.create<DetectionService>()
        val formService = retrofitForm.create<FormService>()
        return DetectionRemoteDataSource(
            detectionsFormRef,
            detectionsExpressionRef,
            firebaseStorage,
            detectionService,
            formService
        )
    }

    @Provides
    @Singleton
    fun provideTfLiteUserClassifierDataSource(
        @ApplicationContext context: Context
    ): TfLiteUserClassifierDataSource {
        return TfLiteUserClassifierDataSource(context)
    }
}
