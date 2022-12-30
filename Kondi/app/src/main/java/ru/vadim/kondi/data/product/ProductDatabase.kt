package ru.vadim.kondi.data.product

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.vadim.kondi.domain.product.ProductItem


@Database(entities = [ProductItem::class], version = 1, exportSchema = false)
abstract class ProductsDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductDao
}