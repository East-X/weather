package com.eastx7.weather.viewmodels

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import com.eastx7.weather.api.openweathermap.OwmRepository
import com.eastx7.weather.data.db.DbDayRepository
import com.eastx7.weather.utilities.Constants.KEEP_FLOW_SUBSCRIBED_IN_MILLIS
import kotlinx.coroutines.launch

@HiltViewModel
internal class DaysListViewModel @Inject constructor(
    private val owmRepository: OwmRepository,
    private val dbDayRepository: DbDayRepository
) : ViewModel() {

    val srvErrorText: StateFlow<Int> = owmRepository.getTextError()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(KEEP_FLOW_SUBSCRIBED_IN_MILLIS), 0)

    val stateDbChange: StateFlow<Int> =
        dbDayRepository.getStateDbChange()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(KEEP_FLOW_SUBSCRIBED_IN_MILLIS),
                0
            )

    fun listOfItems() = dbDayRepository.listOfItems()

    fun populateItemsList(myLocation: Location?) {
        myLocation?.let {
            viewModelScope.launch {
                owmRepository.cacheHistory(it.latitude, it.longitude)
            }
        }
    }

    fun clearTextError() {
        owmRepository.clearTextError()
    }

    fun setTextError(textResource: Int) {
        owmRepository.setTextError(textResource)
    }

    private companion object {
        const val TAG = "DaysListViewModel"
    }
}