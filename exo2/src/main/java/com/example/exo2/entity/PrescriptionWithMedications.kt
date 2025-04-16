package com.example.exo2.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PrescriptionWithMedications(
    @Embedded val prescription: Prescription,

    @Relation(
        parentColumn = "prescriptionId",
        entityColumn = "medicationId",
        associateBy = Junction(PrescriptionMedication::class)
    )
    val medications: List<Medication>
)
