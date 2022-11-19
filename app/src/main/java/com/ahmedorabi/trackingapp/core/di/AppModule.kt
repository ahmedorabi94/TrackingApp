package com.ahmedorabi.trackingapp.core.di

import android.app.Application
import androidx.room.Room
import com.ahmedorabi.trackingapp.core.db.AppDatabase
import com.ahmedorabi.trackingapp.core.db.TripDao
import com.ahmedorabi.trackingapp.core.use_case.TripUseCase
import com.ahmedorabi.trackingapp.core.use_case.TripUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {

        return Room.databaseBuilder(app, AppDatabase::class.java, "trips.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTripDao(db: AppDatabase): TripDao {
        return db.tripDao()
    }

    @Singleton
    @Provides
    fun provideTripUseCase(tripDao: TripDao): TripUseCase {
        return TripUseCaseImpl(tripDao)
    }
}