package com.ch2ps215.mentorheal.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.ch2ps215.data.local.UserLocalDataSource
import com.ch2ps215.data.local.datastore.UserPreferences
import com.ch2ps215.data.local.room.MentorHealDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    private val Context.dataStore by preferencesDataStore(name = "app_preferences")

    @Provides
    @Singleton
    fun providePreferencesManager(
        @ApplicationContext
        context: Context
    ): UserPreferences {
        return UserPreferences(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideUserLocalDataSource(userPreferences: UserPreferences): UserLocalDataSource {
        return UserLocalDataSource(userPreferences)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext
        context: Context
    ): MentorHealDatabase {
        return Room
            .databaseBuilder(context, MentorHealDatabase::class.java, "mentorheal.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}
