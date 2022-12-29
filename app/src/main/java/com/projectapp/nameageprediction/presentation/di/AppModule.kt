package com.projectapp.nameageprediction.presentation.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.projectapp.nameageprediction.data.api.AgifyApi
import com.projectapp.nameageprediction.data.db.NameAgePredictionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

annotation class AppScope

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideAgifyApi(): AgifyApi {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        return retrofit.create(AgifyApi::class.java)
    }

    @Provides
    fun providePredictionsDatabase(context: Context): NameAgePredictionDatabase {
        return Room.databaseBuilder(
            context,
            NameAgePredictionDatabase::class.java,
            "predictions-database"
        ).build()
    }

    private const val BASE_URL = "https://api.agify.io/"

}
