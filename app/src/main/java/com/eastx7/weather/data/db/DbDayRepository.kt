package com.eastx7.weather.data.db

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import com.eastx7.weather.R
import com.eastx7.weather.utilities.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbDayRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val dbDayDao: DbDayDao
) {

    private val stateDbChange = MutableStateFlow(0)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun getStateDbChange(): Flow<Int> {
        return stateDbChange
            .debounce(Constants.DEBOUNS_TIME_UI_UPDATE_IN_MILLIS)
            .filter { it != 0 }
            .flatMapLatest { id ->
                flow {
                    emit(id)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    fun listOfItems(): Flow<List<DbDay>> {
        return dbDayDao.listOfItems(Constants.NUM_DAYS_OF_HISTORY)
    }

    suspend fun insert(item: DbDay): Long =
        withContext(dispatcher) {
            stateDbChange.value = getTimeStamp()
            dbDayDao.insert(item)
        }

    suspend fun update(item: DbDay): Int =
        withContext(dispatcher) {
            stateDbChange.value = getTimeStamp()
            dbDayDao.update(item)
        }

    suspend fun deleteById(refId: Long) {
        withContext(dispatcher) { dbDayDao.deleteById(refId) }
    }

    suspend fun deleteAll() {
        withContext(dispatcher) { dbDayDao.deleteAll() }
    }

    suspend fun getById(refId: Int) =
        withContext(dispatcher) { dbDayDao.getById(refId) }

    fun getTimeStamp(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.HOUR_OF_DAY) * 3600 +
                calendar.get(Calendar.MINUTE) * 60 +
                calendar.get(Calendar.SECOND)
    }
}
