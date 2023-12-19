package com.ch2ps215.mentorheal.di

import com.ch2ps215.mentorheal.data.DefaultArticleRepository
import com.ch2ps215.mentorheal.data.DefaultDetectionRepository
import com.ch2ps215.mentorheal.data.DefaultTrackerRepository
import com.ch2ps215.mentorheal.data.DefaultUserRepository
import com.ch2ps215.mentorheal.data.local.DetectionLocalDataSource
import com.ch2ps215.mentorheal.data.local.TrackerLocalDataSource
import com.ch2ps215.mentorheal.data.local.UserLocalDataSource
import com.ch2ps215.mentorheal.data.remote.ArticleRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.DetectionRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.TfLiteUserClassifierDataSource
import com.ch2ps215.mentorheal.data.remote.TrackerRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.UserRemoteDataSource
import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
import com.ch2ps215.mentorheal.domain.repository.DetectionRepository
import com.ch2ps215.mentorheal.domain.repository.TrackerRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
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
        detectionRemoteDataSource: DetectionRemoteDataSource,
        tfLiteUserClassifierDataSource: TfLiteUserClassifierDataSource
    ): DetectionRepository {
        return DefaultDetectionRepository(
            detectionLocalDataSource,
            detectionRemoteDataSource,
            tfLiteUserClassifierDataSource
        )
    }

    @Provides
    @Singleton
    fun provideTrackerRepository(
        trackerLocalDataSource: TrackerLocalDataSource,
        trackerRemoteDataSource: TrackerRemoteDataSource,
    ): TrackerRepository {
        return DefaultTrackerRepository(
            trackerLocalDataSource,
            trackerRemoteDataSource,
        )
    }


    @Provides
    @Singleton
    fun provideArticleRepository(
        articleRemoteDataSource: ArticleRemoteDataSource
    ): ArticleRepository {
        return DefaultArticleRepository(articleRemoteDataSource)
    }

}
