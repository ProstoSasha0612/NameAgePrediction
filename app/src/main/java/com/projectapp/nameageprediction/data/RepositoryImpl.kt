package com.projectapp.nameageprediction.data

import android.util.Log
import com.projectapp.nameageprediction.data.api.AgifyApi
import com.projectapp.nameageprediction.data.db.NameAgePredictionDatabase
import com.projectapp.nameageprediction.data.models.NameAgePredictionEntity
import com.projectapp.nameageprediction.data.models.Resource
import com.projectapp.nameageprediction.data.models.mapToDomain
import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import com.projectapp.nameageprediction.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val api: AgifyApi,
    val database: NameAgePredictionDatabase,
) : Repository {
    override suspend fun loadNameAgePrediction(name: String): Resource<NameAgePrediction> =
        withContext(Dispatchers.IO) {
            try {
                val result = api.getNameAgePrediction(name).mapToDomain()
                Resource.Success(result)
            } catch (e: Exception) {
                Log.e("MYTAG_ERROR", e.message.toString())
                Resource.Error(e.localizedMessage)
            }
        }

    override suspend fun replaceInsertNameAgePredictionToDb(prediction: NameAgePrediction) =
        withContext(Dispatchers.IO) {
            val entity = mapNameAgePredictionToEntity(prediction)
            database.predictionDao.replaceInsertNameAgePrediction(entity)
        }

    override suspend fun ignoreInsertNameAgePredictionToDb(prediction: NameAgePrediction) =
        withContext(Dispatchers.IO) {
            val entity = mapNameAgePredictionToEntity(prediction)
            database.predictionDao.ignoreInsertNameAgePrediction(entity)
        }

    override suspend fun getFromDbNameAgePrediction(name: String): Resource<NameAgePrediction?> =
        withContext(Dispatchers.IO) {
            try {
                val result = database.predictionDao.getNameAgePrediction(name)?.mapToDomain()
                Resource.Success(result)
            } catch (e: Exception) {
                Log.e("MYTAG_ERROR", e.message.toString())
                Resource.Error(e.localizedMessage)
            }
        }

    override suspend fun deleteNameAgePrediction(prediction: NameAgePrediction) =
        withContext(Dispatchers.IO) {
            val entity = mapNameAgePredictionToEntity(prediction)
            database.predictionDao.deleteNameAgePrediction(entity)
        }

    override suspend fun getFavoritesList(): Resource<List<NameAgePrediction>?> =
        withContext(Dispatchers.IO) {
            try {
                val result = database.predictionDao.getFavoritesList()?.map { element ->
                    element.mapToDomain()
                }
                Resource.Success(result)
            } catch (e: Exception) {
                Log.e("MYTAG_ERROR", e.message.toString())
                Resource.Error(e.localizedMessage)
            }
        }

    companion object {
        fun mapNameAgePredictionToEntity(nameAgePrediction: NameAgePrediction): NameAgePredictionEntity =
            NameAgePredictionEntity(
                age = nameAgePrediction.age,
                name = nameAgePrediction.name,
                isFavorite = nameAgePrediction.isFavorite
            )
    }
}