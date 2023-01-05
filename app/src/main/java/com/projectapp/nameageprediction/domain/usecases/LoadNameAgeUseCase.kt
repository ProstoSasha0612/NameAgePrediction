package com.projectapp.nameageprediction.domain.usecases

import com.projectapp.nameageprediction.data.models.Resource
import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import com.projectapp.nameageprediction.domain.repository.Repository
import javax.inject.Inject

class LoadNameAgeUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(name: String): Resource<NameAgePrediction> {
        return when (val prediction = repository.loadNameAgePrediction(name)) {
            is Resource.Success -> {
                repository.ignoreInsertNameAgePredictionToDb(requireNotNull(prediction.data))
                val res = repository.getFromDbNameAgePrediction(prediction.data.name)
                Resource.Success(res.data)
            }
            is Resource.Error -> {
                val predictionFromDb = repository.getFromDbNameAgePrediction(name)
                if (predictionFromDb is Resource.Success) {
                    Resource.Success(predictionFromDb.data)
                } else {
                    Resource.Error(prediction.message)
                }
            }
        }
    }
}
