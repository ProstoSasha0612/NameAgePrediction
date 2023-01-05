package com.projectapp.nameageprediction.data.models

import android.util.Log
import com.projectapp.nameageprediction.domain.models.NameAgePrediction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NameAgePredictionResponse(
    @SerialName("age") var age: Int?,
    @SerialName("name") val name: String,
)

fun NameAgePredictionResponse.mapToEntity(): NameAgePredictionEntity = NameAgePredictionEntity(
    name = this.name,
    age = this.age,
    isFavorite = false
)

fun NameAgePredictionResponse.mapToDomain(): NameAgePrediction {
    Log.d("MYTAG","mapping response from api into domain")
    return NameAgePrediction(
        name = this.name,
        age = this.age,
        isFavorite = false
    )
}

