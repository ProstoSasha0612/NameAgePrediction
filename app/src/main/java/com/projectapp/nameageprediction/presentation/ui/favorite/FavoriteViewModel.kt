package com.projectapp.nameageprediction.presentation.ui.favorite

import android.annotation.SuppressLint
import androidx.lifecycle.*
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import com.projectapp.nameageprediction.domain.usecases.DeleteFromFavoriteUseCase
import com.projectapp.nameageprediction.domain.usecases.GetFavoritesPredictionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesPredictionsUseCase: GetFavoritesPredictionsUseCase,
    private val _predictionsAdapter: PredictionsRecyclerViewAdapter,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
) : ViewModel() {

    //    private val _showDeleteButtonFlow: MutableStateFlow<Boolean> = predictionsAdapter.isShowCheckboxesState
    val showDeleteButtonState: StateFlow<Boolean> = _predictionsAdapter.isShowCheckboxesState
    val predictionsAdapter: ListAdapter<NameAgePrediction, PredictionsRecyclerViewAdapter.PredictionsViewHolder>
        get() = _predictionsAdapter

    init {
        observeFavoritesList()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteSelectedItems() {

        viewModelScope.launch {
//            deleteFromFavoriteUseCase(_predictionsAdapter.currentList)
        }

        _predictionsAdapter.currentList.forEach { prediction ->
        }


        _predictionsAdapter.isShowCheckboxesState.value = false
        _predictionsAdapter.notifyDataSetChanged()
    }


    fun observeFavoritesList() {
        viewModelScope.launch {
            getFavoritesPredictionsUseCase().collect { list ->
                predictionsAdapter.submitList(list)
            }
        }
    }

}