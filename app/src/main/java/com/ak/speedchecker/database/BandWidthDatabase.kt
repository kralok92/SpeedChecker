package com.ak.speedchecker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ak.speedchecker.dao.SpeedDao
import com.ak.speedchecker.model.Speed

@Database(entities = [Speed::class], version = 1)
abstract class BandWidthDatabase : RoomDatabase() {
    abstract  fun getSpeedDao():SpeedDao
}