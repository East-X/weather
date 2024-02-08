package com.eastx7.weather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.eastx7.weather.data.db.DbDay
import com.eastx7.weather.data.db.DbDayRepository
import javax.inject.Inject

@HiltViewModel
internal class DaysItemViewModel @Inject constructor(
    private val dbDayRepository: DbDayRepository
) : ViewModel() {

    private val _dbDay = MutableStateFlow<DbDay?>(null)
    val dbDay: StateFlow<DbDay?> = _dbDay.asStateFlow()

    fun setDayItemId(dayId: Int) {
        if (dayId != 0) {
            viewModelScope.launch(Dispatchers.IO) {
                _dbDay.value = dbDayRepository.getById(dayId)
            }
        }
    }
}