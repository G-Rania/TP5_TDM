package com.example.exo2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medication(
    @PrimaryKey(autoGenerate = true)
    val medicationId: Int? = null,
    val name: String,
    val dosage: String,
    val instructions: String?
)