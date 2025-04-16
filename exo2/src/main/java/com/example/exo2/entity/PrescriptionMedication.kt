package com.example.exo2.entity

import androidx.room.Entity


@Entity(
    primaryKeys = ["prescriptionId", "medicationId"],
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Prescription::class,
            parentColumns = ["prescriptionId"],
            childColumns = ["prescriptionId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        ),
        androidx.room.ForeignKey(
            entity = Medication::class,
            parentColumns = ["medicationId"],
            childColumns = ["medicationId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class PrescriptionMedication(
    val prescriptionId: Int,
    val medicationId: Int
)
