package com.example.exo2.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Patient (
    @PrimaryKey(autoGenerate = true)
    val patientId:Int = 0,
    val firstName:String,
    val lastName:String
)