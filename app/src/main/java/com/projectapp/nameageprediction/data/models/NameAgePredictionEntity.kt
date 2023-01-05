package com.projectapp.nameageprediction.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.projectapp.nameageprediction.domain.models.NameAgePrediction

@Entity(tableName = "predictions")
data class NameAgePredictionEntity(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false,
)

fun NameAgePredictionEntity.mapToDomain(): NameAgePrediction = NameAgePrediction(
    name = this.name,
    age = this.age,
    isFavorite = this.isFavorite
)
