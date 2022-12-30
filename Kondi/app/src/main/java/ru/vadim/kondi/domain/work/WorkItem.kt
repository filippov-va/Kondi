package ru.vadim.kondi.domain.work

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.vadim.kondi.data.Constants
import ru.vadim.kondi.domain.KondiConverters
import java.util.Date
@TypeConverters(KondiConverters::class)
@Entity(tableName = Constants.WORKS_DATABASE_TABLE)
data class WorkItem(
    val date: Date, //дата
    var product_item: String, //наименование продукта
    val volume: Int, //количество коробок
    val mass: Double, //масса
    val sum: Double, //сумма
    val expanded: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    var id: Int = UNDEFINED_ID //идентификатор
){
    companion object{
        const val UNDEFINED_ID = 0
    }
}
