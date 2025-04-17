package com.example.exo2.room

import androidx.room.*
import com.example.exo2.entity.*

@Dao
interface PrescriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrescription(prescription: Prescription): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrescriptionMedicationCrossRef(crossRef: PrescriptionMedication)

    @Query("SELECT * FROM Prescription WHERE prescriptionId = :id")
    suspend fun getPrescriptionById(id: Int): Prescription

    @Transaction
    @Query("SELECT * FROM Prescription WHERE prescriptionId = :id")
    suspend fun getPrescriptionWithMedications(id: Int): PrescriptionWithMedications

    @Transaction
    @Query("SELECT * FROM Prescription")
    suspend fun getAllPrescriptionsWithMedications(): List<PrescriptionWithMedications>
}
