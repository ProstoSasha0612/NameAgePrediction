package com.projectapp.nameageprediction.domain.usecases

import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import com.projectapp.nameageprediction.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesPredictionsUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(): Flow<List<NameAgePrediction>?> = repository.getFavoritesList()

}