package ru.vadim.kondi.data.products

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.vadim.kondi.domain.product.ProductItem

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products_table")
    fun getProductsList(): LiveData<List<ProductItem>>

    @Query("SELECT * FROM products_table WHERE id = :productId")
    fun getProduct(productId: Int): Flow<ProductItem>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(productItem: ProductItem)

    @Update
    suspend fun updateProduct(productItem: ProductItem)

    @Delete
    suspend fun deleteProduct(productItem: ProductItem)

    @Query("DELETE FROM products_table")
    suspend fun deleteAllProducts()




}