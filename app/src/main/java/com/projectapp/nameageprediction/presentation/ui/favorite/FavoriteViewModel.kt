package com.projectapp.nameageprediction.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import com.projectapp.nameageprediction.domain.usecases.GetFavoritesPredictionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesPredictionsUseCase: GetFavoritesPredictionsUseCase,
) : ViewModel() {

    suspend fun getFavoriteListFlow(): Flow<List<NameAgePrediction>?> {
        return getFavoritesPredictionsUseCase()
    }

}