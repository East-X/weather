package com.eastx7.weather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import com.eastx7.weather.utilities.Converters
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module

internal class UtilitiesModule {
    @Provides
    fun provideConverters(): Converters = Converters()
}