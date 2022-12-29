package com.projectapp.nameageprediction.data

import com.projectapp.nameageprediction.data.models.Resource
import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import com.projectapp.nameageprediction.domain.repository.Repository

class RepositoryImpl : Repository {
    override suspend fun loadNameAgePrediction(name: String): Resource<NameAgePrediction> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNameAgePredictionToDb(prediction: NameAgePrediction) {
        TODO("Not yet implemented")
    }

    override suspend fun getFromDbNameAgePrediction(name: String): Resource<NameAgePrediction?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNameAgePrediction(prediction: NameAgePrediction) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoritesList(): Resource<List<NameAgePrediction>> {
        TODO("Not yet implemented")
    }
}