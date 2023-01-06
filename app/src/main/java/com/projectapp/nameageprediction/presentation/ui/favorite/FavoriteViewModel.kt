package com.projectapp.nameageprediction.presentation.ui.favorite

import androidx.lifecycle.*
import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import com.projectapp.nameageprediction.domain.usecases.GetFavoritesPredictionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesPredictionsUseCase: GetFavoritesPredictionsUseCase,
) : ViewModel() {

    val predictionsAdapter = PredictionsRecyclerViewAdapter()

    fun observeFavoritesList() {
        viewModelScope.launch {
            getFavoritesPredictionsUseCase().collect { list ->
                predictionsAdapter.submitList(list)
            }
        }
    }

}