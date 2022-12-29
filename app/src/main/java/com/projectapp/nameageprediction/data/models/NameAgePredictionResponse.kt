package com.projectapp.nameageprediction.data.models

import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NameAgePredictionResponse(
    @SerialName("age") val age: Int,
    @SerialName("name") val name: String,
)

fun NameAgePredictionResponse.mapToEntity(): NameAgePredictionEntity = NameAgePredictionEntity(
    name = this.name,
    age = this.age,
    isFavorite = false
)

fun NameAgePredictionResponse.mapToDomain(): NameAgePrediction = NameAgePrediction(
    name = this.name,
    age = this.age,
    isFavorite = false
)

