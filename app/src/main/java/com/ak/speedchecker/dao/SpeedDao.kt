package com.ak.speedchecker.dao

import androidx.room.*
import com.ak.speedchecker.model.Speed
import java.util.*

@Dao
interface SpeedDao {

    @Query("SELECT * FROM speed")
    suspend fun getAll(): List<Speed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(speed: Speed?)

    @Delete
    suspend fun delete(speed: Speed)

    @Query("DELETE FROM speed")
    suspend fun deleteAllSpeed()

    @Update
    suspend fun update(speed: Speed)

}