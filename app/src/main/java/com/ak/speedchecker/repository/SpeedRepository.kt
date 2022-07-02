package com.ak.speedchecker.repository

import com.ak.speedchecker.dao.SpeedDao
import com.ak.speedchecker.model.Speed
import javax.inject.Inject

class SpeedRepository @Inject constructor(private val speedDao: SpeedDao){


    suspend fun getAll() = speedDao.getAll()

    suspend fun insertSpeed(speed: Speed) = speedDao.insert(speed)

    suspend fun deleteSpeed(speed: Speed) = speedDao.delete(speed)

    suspend fun updateSpeed(speed: Speed) = speedDao.update(speed)

    suspend fun deleteAllSpeed() = speedDao.deleteAllSpeed()
}