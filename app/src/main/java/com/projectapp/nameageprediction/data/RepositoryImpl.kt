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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: AgifyApi,
    private val database: NameAgePredictionDatabase,
) : Repository {
    override suspend fun loadNameAgePrediction(name: String): Resource<NameAgePrediction> =
        withContext(Dispatchers.IO) {
            try {
                val result = api.getNameAgePrediction(name).mapToDomain()
                if (result.age == null) {
                    Resource.Error("Can't find predcition for this name: $name")
                } else {
                    Resource.Success(result)
                }
            } catch (e: Exception) {
                Log.e("MYTAG_ERROR", e.message.toString())
                Resource.Error(e.localizedMessage)
            }
        }

    override suspend fun replaceInsertNameAgePredictionToDb(prediction: NameAgePrediction?): Unit =
        withContext(Dispatchers.IO) {
            prediction?.let {
                val entity = mapNameAgePredictionToEntity(prediction)
                database.predictionDao.replaceInsertNameAgePrediction(entity)
            }
        }

    override suspend fun ignoreInsertNameAgePredictionToDb(prediction: NameAgePrediction) =
        withContext(Dispatchers.IO) {
            val entity = mapNameAgePredictionToEntity(prediction)
            database.predictionDao.ignoreInsertNameAgePrediction(entity)
        }

    override suspend fun getFromDbNameAgePrediction(name: String): Resource<NameAgePrediction?> =
        withContext(Dispatchers.IO) {
            try {
                val result =
                    database.predictionDao.getNameAgePrediction(name)?.get(0)?.mapToDomain()
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

    override suspend fun getFavoritesList(): Flow<List<NameAgePrediction>?> =
        withContext(Dispatchers.IO) {
            database.predictionDao.getFavoritesList().flatMapConcat { list ->
                val mappedList = list?.map { element ->
                    element.mapToDomain()
                }
                flowOf(mappedList)
            }
        }

    companion object {
        fun mapNameAgePredictionToEntity(nameAgePrediction: NameAgePrediction): NameAgePredictionEntity =
            NameAgePredictionEntity(
                age = nameAgePrediction.age ?: -1,
                name = nameAgePrediction.name,
                isFavorite = nameAgePrediction.isFavorite
            )
    }
}
