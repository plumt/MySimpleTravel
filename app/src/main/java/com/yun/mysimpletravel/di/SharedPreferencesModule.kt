package com.yun.mysimpletravel.di

import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPref(): PreferenceUtil {
        return PreferenceUtil
    }
}