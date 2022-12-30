package ru.vadim.kondi.domain.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vadim.kondi.data.Constants

@Entity(tableName = Constants.PRODUCTS_DATABASE_TABLE)
data class ProductItem(
    val item: String, //наименование продукта
    val volume: Int, //количество коробок
    val kilogram: Int, //килограм
    val gram: Int, //грам
    val rubles: Int, //рублей
    val kopeck: Int, //копеек
    val expanded: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    var id: Int = UNDEFINED_ID, //идентификатор

) {
    companion object{
        const val UNDEFINED_ID = 0
    }
}

