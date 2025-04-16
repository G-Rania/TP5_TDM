package com.example.exo2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Patient::class,
            parentColumns = ["patientId"],
            childColumns = ["patientId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class Prescription(
    @PrimaryKey(autoGenerate = true)
    val prescriptionId: Int? = null,
    val patientId: Int,
    val date: Date?,
)