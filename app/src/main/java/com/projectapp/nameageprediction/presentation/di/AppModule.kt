package com.projectapp.nameageprediction.presentation.di

import android.app.Application
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


annotation class AppScope

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideAgifyApi(): AgifyApi {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }

        /**/
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        /**/

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()

        return retrofit.create(AgifyApi::class.java)
    }

    @Provides
    fun providePredictionsDatabase(app: Application): NameAgePredictionDatabase {
        return Room.databaseBuilder(
            app,
            NameAgePredictionDatabase::class.java,
            "predictions-database"
        ).build()
    }

    private const val BASE_URL = "https://api.agify.io/"

}
