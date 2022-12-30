package ru.vadim.kondi.domain.worksinparts

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.vadim.kondi.data.Constants
import ru.vadim.kondi.domain.KondiConverters
import ru.vadim.kondi.domain.work.WorkItem

@TypeConverters(KondiConverters::class)

@Entity(tableName = Constants.PERIOD_DATABASE_TABLE)
data class PeriodItem(
    val dateOpen: Long?,
    val dateClose: Long?,

    val sum: Double? = null,
    val sumSalary: Double?= null,

    val worksList: List<WorkItem>?,
    val description: String = "",

    val inArchive: Boolean = UNDEFINED_inArchive,

    @PrimaryKey(autoGenerate = true)
    var id: Int = UNDEFINED_ID //идентификатор
){
    companion object{
        const val UNDEFINED_ID = 0
        const val UNDEFINED_inArchive = false
    }
}
