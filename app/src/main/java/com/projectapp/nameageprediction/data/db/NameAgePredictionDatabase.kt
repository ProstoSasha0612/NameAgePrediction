package com.projectapp.nameageprediction.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.projectapp.nameageprediction.data.models.NameAgePredictionEntity

@Database(entities = [NameAgePredictionEntity::class], version = 1)
abstract class NameAgePredictionDatabase : RoomDatabase() {

    abstract val predictionDao: NameAgePredictionDao

}
