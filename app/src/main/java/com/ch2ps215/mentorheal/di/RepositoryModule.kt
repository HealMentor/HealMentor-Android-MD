package com.ch2ps215.mentorheal.di

import com.ch2ps215.mentorheal.data.remote.ArticleRemoteDataSource
import com.ch2ps215.mentorheal.data.DefaultArticleRepository
import com.ch2ps215.mentorheal.data.DefaultDetectionRepository
import com.ch2ps215.mentorheal.data.DefaultUserRepository
import com.ch2ps215.mentorheal.data.local.DetectionLocalDataSource
import com.ch2ps215.mentorheal.data.local.UserLocalDataSource
import com.ch2ps215.mentorheal.data.remote.DetectionRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.UserRemoteDataSource
import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
import com.ch2ps215.mentorheal.domain.repository.DetectionRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import com.google.firebase.firestore.CollectionReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository {
        return DefaultUserRepository(userLocalDataSource, userRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideDetectionRepository(
        detectionLocalDataSource: DetectionLocalDataSource,
        detectionRemoteDataSource: DetectionRemoteDataSource
    ): DetectionRepository {
        return DefaultDetectionRepository(detectionLocalDataSource, detectionRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideArticleRepository(articleRemoteDataSource: ArticleRemoteDataSource): ArticleRepository {
        return DefaultArticleRepository(articleRemoteDataSource)
    }
}
