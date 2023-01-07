package com.projectapp.nameageprediction.domain.usecases

import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import com.projectapp.nameageprediction.domain.repository.Repository
import javax.inject.Inject

class DeleteFromFavoriteUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(vararg predictions: NameAgePrediction) {
        predictions.forEach { prediction ->
            repository.replaceInsertNameAgePredictionToDb(prediction.copy(isFavorite = false))
        }
    }

}
