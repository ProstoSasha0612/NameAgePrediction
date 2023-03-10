package com.projectapp.nameageprediction.domain.repository

import com.projectapp.nameageprediction.data.models.Resource
import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun loadNameAgePrediction(name: String): Resource<NameAgePrediction>

    suspend fun replaceInsertNameAgePredictionToDb(prediction: NameAgePrediction?)

    suspend fun ignoreInsertNameAgePredictionToDb(prediction: NameAgePrediction)

    suspend fun getFromDbNameAgePrediction(name: String): Resource<NameAgePrediction?>

    suspend fun deleteNameAgePrediction(prediction: NameAgePrediction)

    suspend fun getFavoritesList(): Flow<List<NameAgePrediction>?>
}
