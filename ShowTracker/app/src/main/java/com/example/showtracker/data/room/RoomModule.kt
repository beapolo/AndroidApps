package com.example.showtracker.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.showtracker.data.common.Constants
import com.example.showtracker.data.dao.*
import com.example.showtracker.data.model.showepisode.EpisodeEntity
import com.example.showtracker.data.model.ShowEntity
import com.example.showtracker.data.model.showactor.ActorEntity
import com.example.showtracker.data.model.showactor.ShowActorCrossRef
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
    ): TrackerRoomDatabase =
        Room.databaseBuilder(context, TrackerRoomDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesShowsDao(database: TrackerRoomDatabase): ShowsDao =
        database.showsDao()

    @Provides
    fun providesActorsDao(database: TrackerRoomDatabase): ActorsDao =
        database.actorsDao()

    @Provides
    fun providesEpisodesDao(database: TrackerRoomDatabase): EpisodesDao =
        database.episodesDao()

    @Provides
    fun providesShowEpisodesDao(database: TrackerRoomDatabase): ShowEpisodesDao =
        database.showEpisodesDao()

    @Provides
    fun providesShowActorDao(database: TrackerRoomDatabase): ShowsActorsDao =
        database.showActorDao()
}

@Database(
    entities = [ShowEntity::class,
        EpisodeEntity::class,
        ActorEntity::class,
        ShowActorCrossRef::class],
    version = 7,
    exportSchema = true
)
abstract class TrackerRoomDatabase : RoomDatabase() {
    abstract fun showsDao(): ShowsDao
    abstract fun actorsDao(): ActorsDao
    abstract fun episodesDao(): EpisodesDao
    abstract fun showEpisodesDao(): ShowEpisodesDao
    abstract fun showActorDao(): ShowsActorsDao
}

