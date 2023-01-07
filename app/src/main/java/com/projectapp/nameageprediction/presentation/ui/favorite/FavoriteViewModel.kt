package com.projectapp.nameageprediction.presentation.ui.favorite

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ListAdapter
import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import com.projectapp.nameageprediction.domain.usecases.DeleteFromFavoriteUseCase
import com.projectapp.nameageprediction.domain.usecases.GetFavoritesPredictionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesPredictionsUseCase: GetFavoritesPredictionsUseCase,
    private val _predictionsAdapter: PredictionsRecyclerViewAdapter,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
) : ViewModel() {

    val showDeleteButtonState: StateFlow<Boolean> = _predictionsAdapter.isShowCheckboxesState
    val predictionsAdapter: ListAdapter<NameAgePrediction, PredictionsRecyclerViewAdapter.PredictionsViewHolder>
        get() = _predictionsAdapter

    @SuppressLint("NotifyDataSetChanged")
    fun deleteSelectedItems() {
        viewModelScope.launch {
            _predictionsAdapter.currentList.forEach { prediction ->
                if (prediction.isChecked) {
                    deleteFromFavoriteUseCase(prediction)
                }
            }
            getFavoritesPredictionsUseCase().collect { list ->
                Log.d("MYTAG","DATA COLLECTED")
                predictionsAdapter.submitList(list)
            }
        }
        _predictionsAdapter.isShowCheckboxesState.value = false
        _predictionsAdapter.notifyDataSetChanged()
    }

    fun observeFavoritesList() {
        viewModelScope.launch {
            getFavoritesPredictionsUseCase().collect { list ->
                Log.d("MYTAG","DATA COLLECTED")
                predictionsAdapter.submitList(list)
            }
        }
    }

}
