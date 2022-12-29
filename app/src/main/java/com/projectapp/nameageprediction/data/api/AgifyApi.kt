package com.projectapp.nameageprediction.data.api

import com.projectapp.nameageprediction.data.models.NameAgePredictionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AgifyApi {

    @GET
    suspend fun getNameAgePrediction(@Query("name") name: String): NameAgePredictionResponse
}
