package com.projectapp.nameageprediction.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.projectapp.nameageprediction.data.models.NameAgePredictionEntity

@Dao
interface NameAgePredictionDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveNameAgePrediction(predictionEntity: NameAgePredictionEntity)

    @Query("SELECT * FROM predictions WHERE name =:name")
    suspend fun getNameAgePrediction(name: String): NameAgePredictionEntity

    @Delete
    suspend fun deleteNameAgePrediction(vararg predictionEntities: NameAgePredictionEntity)

    //1 in SQLite equals to true
    @Query("SELECT * FROM predictions WHERE isFavorite = 1")
    suspend fun getFavoritesList(): List<NameAgePredictionEntity>

}
