package ru.vadim.kondi.di

import android.content.Context
import androidx.room.Room
import androidx.room.TypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.vadim.kondi.data.Constants
import ru.vadim.kondi.data.worksinparts.WorksInPartsDatabase
import ru.vadim.kondi.domain.KondiConverters

@TypeConverters(KondiConverters::class)
@Module
@InstallIn(SingletonComponent::class)
object WorksInPairDatabaseModule {




    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, WorksInPartsDatabase::class.java, Constants.WORKS_IN_PARTS_DATABASE_NAME
    )

        .build()


    @Provides
    fun provideDao(database: WorksInPartsDatabase) = database.worksInPairDao()

}