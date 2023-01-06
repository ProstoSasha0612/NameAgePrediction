package com.projectapp.nameageprediction.domain.usecases

import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import com.projectapp.nameageprediction.domain.repository.Repository
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(prediction: NameAgePrediction?) {
        val predictionToInsert = prediction?.copy(isFavorite = true)
        repository.replaceInsertNameAgePredictionToDb(predictionToInsert)
    }
}