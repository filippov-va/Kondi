package ru.vadim.kondi.data.period

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.vadim.kondi.domain.worksinparts.PeriodItem

@Database(entities = [PeriodItem::class], version = 1, exportSchema = false)

abstract class WorksInPartsDatabase : RoomDatabase() {

    abstract fun worksInPartsDao(): PeriodDao
}