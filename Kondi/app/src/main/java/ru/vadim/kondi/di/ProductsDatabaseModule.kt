package ru.vadim.kondi.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.vadim.kondi.data.products.ProductsDatabase
import ru.vadim.kondi.data.Constants.PRODUCTS_DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductsDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context) = Room.databaseBuilder(
        context, ProductsDatabase::class.java,
        PRODUCTS_DATABASE_NAME
    )

        .build()

    @Singleton
    @Provides
    fun provideDao(database: ProductsDatabase) = database.productsDao()
}