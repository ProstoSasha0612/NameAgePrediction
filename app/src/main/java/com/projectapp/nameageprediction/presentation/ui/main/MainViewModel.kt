package com.projectapp.nameageprediction.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectapp.nameageprediction.data.models.Resource
import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import com.projectapp.nameageprediction.domain.usecases.LoadNameAgeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val loadNameAgeUseCase: LoadNameAgeUseCase) : ViewModel() {

    private val _agePredictionState: MutableStateFlow<AgePredictionState> =
        MutableStateFlow(AgePredictionState.Empty)
    val agePredictionState: StateFlow<AgePredictionState> get() = _agePredictionState

    fun searchPrediction(name: String?) {
        name?.let {
            viewModelScope.launch {
                when (val result = loadNameAgeUseCase(name)) {
                    is Resource.Success -> {
                        _agePredictionState.value =
                            AgePredictionState.Success(requireNotNull(result.data).age!!)
                    }
                    is Resource.Error -> {
                        _agePredictionState.value = AgePredictionState.Error(result.message)
                    }
                }
            }
        }

    }
}

sealed class AgePredictionState {
    object Empty : AgePredictionState()
    class Success(val age: Int) : AgePredictionState()
    class Error(val message: String?) : AgePredictionState()
}