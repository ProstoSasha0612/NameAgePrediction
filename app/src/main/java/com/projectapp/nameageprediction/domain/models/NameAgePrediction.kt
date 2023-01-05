package com.projectapp.nameageprediction.domain.models

data class NameAgePrediction(
    val age: Int?,
    val name: String,
    var isFavorite: Boolean = false,
)