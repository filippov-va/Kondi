package ru.vadim.kondi.data.period

import androidx.lifecycle.LiveData
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import ru.vadim.kondi.domain.worksinparts.PeriodItem
import javax.inject.Inject


@ViewModelScoped
class WorksInPartsRepository @Inject constructor(
    private val periodDao: PeriodDao
) {

    val getWorksInPartsList: LiveData<List<PeriodItem>> = periodDao.getPeriodList()

    fun getWorksInParts(workInPartsId: Int): Flow<PeriodItem> {
        return periodDao.getPeriod(workInPartsId)
    }

    suspend fun addWorksInParts(periodItem: PeriodItem) {
        periodDao.addPeriod(periodItem)
    }

    suspend fun updateWorksInParts(periodItem: PeriodItem) {
        periodDao.updatePeriod(periodItem)
    }

    suspend fun deleteWorksInParts(periodItem: PeriodItem) {
        periodDao.deletePeriod(periodItem)
    }

    suspend fun deleteAllWorksInParts() {
        periodDao.deleteAllPeriods()
    }


}