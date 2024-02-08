package com.eastx7.weather

import com.eastx7.weather.utilities.Converters
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    val conv = Converters()

    @Test
    fun epochToDayId_isCorrect() {
        assertEquals(conv.epochToDayId(1707228000), 2024037)
    }
}