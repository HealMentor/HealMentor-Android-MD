package com.ch2ps215.mentorheal.di

import com.ch2ps215.data.DefaultUserRepository
import com.ch2ps215.data.local.FormLocalDataSource
import com.ch2ps215.data.local.UserLocalDataSource
import com.ch2ps215.data.remote.UserRemoteDataSource
import com.ch2ps215.mentorheal.data.repository.FormRepository
import com.ch2ps215.mentorheal.domain.repository.IFormRepository
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
    fun provideFormRepository(
        formLocalDataSource: FormLocalDataSource
    ): IFormRepository {
        return FormRepository(formLocalDataSource)
    }
}
