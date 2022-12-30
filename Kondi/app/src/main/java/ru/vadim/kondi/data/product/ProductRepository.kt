package ru.vadim.kondi.data.product

import androidx.lifecycle.LiveData
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import ru.vadim.kondi.domain.product.ProductItem

import javax.inject.Inject


@ViewModelScoped
class ProductsRepository @Inject constructor  (
    private val productDao: ProductDao
        )  {

    val getProductsList: LiveData<List<ProductItem>> = productDao.getProductList()

    fun getProduct(productId: Int): Flow<ProductItem> {
        return productDao.getProduct(productId)
    }

    suspend fun addProduct(productItem: ProductItem) {
        productDao.addProduct(productItem)
    }

    suspend fun updateProduct(productItem: ProductItem) {
        productDao.updateProduct(productItem)
    }

    suspend fun deleteProduct(productItem: ProductItem) {
        productDao.deleteProduct(productItem)
    }

    suspend fun deleteAllProducts() {
        productDao.deleteAllProducts()
    }


}