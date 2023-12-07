package com.ch2ps215.mentorheal.di

import com.ch2ps215.mentorheal.data.ArticleRemoteDataSource
import com.ch2ps215.mentorheal.data.DefaultArticleRepository
import com.ch2ps215.mentorheal.data.DefaultUserRepository
import com.ch2ps215.mentorheal.data.local.UserLocalDataSource
import com.ch2ps215.mentorheal.data.remote.UserRemoteDataSource
import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
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
    ): UserRepository = TODO()

    @Provides
    @Singleton
    fun provideArticleRepository(articleRemoteDataSource: ArticleRemoteDataSource): ArticleRepository = TODO()
}
