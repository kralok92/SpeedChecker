package com.ak.speedchecker.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "speed")
data class Speed(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    val download: String?,
    val upload: String?,
    val total: String?
)