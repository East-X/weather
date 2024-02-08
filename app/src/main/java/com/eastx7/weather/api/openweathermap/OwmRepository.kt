package com.eastx7.weather.api.openweathermap

import android.util.Log
import com.eastx7.weather.BuildConfig
import com.eastx7.weather.R
import com.eastx7.weather.data.db.DbDay
import com.eastx7.weather.data.db.DbDayRepository
import com.eastx7.weather.utilities.Constants
import com.eastx7.weather.utilities.Converters
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OwmRepository @Inject constructor(
    private val conv: Converters,
    private var serviceGson: GsonService,
    private val dbDayRepository: DbDayRepository
) {
    private val httpResponseText = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun getTextError(): Flow<Int> {
        return httpResponseText
            .debounce(Constants.DEBOUNS_TIME_UI_UPDATE_IN_MILLIS)
            .flatMapLatest { code ->
                flow {
                    emit(code)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    fun clearTextError() {
        httpResponseText.value = 0
    }

    fun setTextError(textResource: Int) {
        httpResponseText.value = textResource
    }

    private fun onHttpResponse(code: Int) {
        httpResponseText.value = when (code) {
            200 -> 0
            400, 401 -> R.string.response_401
            204 -> R.string.response_204
            404 -> R.string.response_404
            else -> R.string.unknown_exception
        }
    }

    private fun onHttpError(methodName: String, logMessage: String, uiMessage: Int) {
        Log.e(TAG, "$methodName: $logMessage")
        httpResponseText.value = uiMessage
    }

    suspend fun cacheHistory(lat: Double, lon: Double) {
        val methodName = "cacheHistory"
        try {
            val response = serviceGson.getHistory(
                "metric",
                lat,
                lon,
                BuildConfig.API_WEATHER_KEY
            )
            val responseCode = response.code()
            val history = response.body()
            if (responseCode == 204) {// no items
                return
            }
            onHttpResponse(responseCode)
            history?.let {
                for (item in it.days) {
                    val dbDay = DbDay(
                        id = conv.epochToDayId(item.epoch),
                        epoch = item.epoch,
                        pressure = item.parameters.pressure,
                        cloudCover = item.cloudCover.percent,
                        humidity = item.parameters.humidity,
                        temperatureMinimum = item.parameters.temperatureMinimum,
                        temperatureMaximum = item.parameters.temperatureMaximum,
                        windSpeed = item.wind.speed
                    )
                    dbDayRepository.insert(dbDay)
                }
            }
        } catch (e: UnknownHostException) {
            onHttpError(methodName, "unknown_host", R.string.unknown_host)
        } catch (e: ConnectException) {
            onHttpError(methodName, "no_internet", R.string.no_internet)
        } catch (e: Exception) {
            onHttpError(methodName, "error: " + e.message, R.string.unknown_exception)
        }
    }

    private companion object {
        const val TAG = "OwmRepository"
    }
}
