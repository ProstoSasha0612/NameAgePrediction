package com.projectapp.nameageprediction.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.projectapp.nameageprediction.data.models.NameAgePredictionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NameAgePredictionDao {

    @Insert(onConflict = REPLACE)
    suspend fun replaceInsertNameAgePrediction(predictionEntity: NameAgePredictionEntity)

    @Insert(onConflict = IGNORE)
    suspend fun ignoreInsertNameAgePrediction(predictionEntity: NameAgePredictionEntity)

    @Query("SELECT * FROM predictions WHERE name =:name")
    suspend fun getNameAgePrediction(name: String): List<NameAgePredictionEntity>?

    @Delete
    suspend fun deleteNameAgePrediction(vararg predictionEntities: NameAgePredictionEntity)

    //1 in SQLite equals to true
    @Query("SELECT * FROM predictions WHERE isFavorite = 1")
    fun getFavoritesList(): Flow<List<NameAgePredictionEntity>?>

}
