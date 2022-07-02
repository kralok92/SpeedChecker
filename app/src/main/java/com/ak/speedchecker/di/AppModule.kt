package com.ak.speedchecker.di

import android.content.Context
import androidx.room.Room
import com.ak.speedchecker.dao.SpeedDao
import com.ak.speedchecker.database.BandWidthDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context):BandWidthDatabase =
        Room.databaseBuilder(context,BandWidthDatabase::class.java,"BandWidthDatabase")
            .build()

    @Provides
    fun providesSpeedDao(database: BandWidthDatabase) : SpeedDao =
        database.getSpeedDao()

}