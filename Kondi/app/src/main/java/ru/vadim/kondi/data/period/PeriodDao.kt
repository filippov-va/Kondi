package ru.vadim.kondi.data.worksinparts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.vadim.kondi.domain.worksinparts.WorksInPartsItem

@Dao
interface WorksInPartsDao {


    @Query("SELECT * FROM works_in_parts_table")
    fun getWorksInPartsList(): LiveData<List<WorksInPartsItem>>

    @Query("SELECT * FROM works_in_parts_table WHERE id = :worksListId")
    fun getWorksInParts(worksListId: Int): Flow<WorksInPartsItem>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorksInParts(worksInPartsItem: WorksInPartsItem)

    @Update
    suspend fun updateWorksInParts(worksInPartsItem: WorksInPartsItem)

    @Delete
    suspend fun deleteWorksInParts(worksInPartsItem: WorksInPartsItem)

    @Query("DELETE FROM works_in_parts_table")
    suspend fun deleteAllWorksInParts()


}