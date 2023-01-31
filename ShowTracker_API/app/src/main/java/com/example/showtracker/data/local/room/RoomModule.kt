package com.example.showtracker.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.showtracker.data.common.Constants
import com.example.showtracker.data.local.*
import com.example.showtracker.data.model.entities.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Named(Constants.ASSET)
    fun getAssetDB(): String = Constants.DATABASE

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): LocalDatabase =
        Room.databaseBuilder(context, LocalDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesShowsDao(database: LocalDatabase): ShowsDao =
        database.showsDao()

    @Provides
    fun providesSeasonDao(database: LocalDatabase): SeasonsDao =
        database.seasonsDao()
}

@Database(
    entities = [ShowEntity::class, SeasonEntity::class],
    version = 12,
    exportSchema = true
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun showsDao(): ShowsDao
    abstract fun seasonsDao(): SeasonsDao
}

