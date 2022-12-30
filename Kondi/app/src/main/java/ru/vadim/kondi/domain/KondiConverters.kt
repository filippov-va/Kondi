package ru.vadim.kondi.domain

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.vadim.kondi.domain.work.WorkItem
import java.util.Date

class KondiConverters {



    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }



    @TypeConverter
    fun listToJson(value: List<WorkItem>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<WorkItem>::class.java).toList()

}