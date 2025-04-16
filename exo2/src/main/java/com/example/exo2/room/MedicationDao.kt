package com.example.exo2.room

import androidx.room.*
import com.example.exo2.entity.Medication

@Dao
interface MedicationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: Medication): Long

    @Query("SELECT * FROM Medication WHERE medicationId = :id")
    suspend fun getMedicationById(id: Int): Medication

    @Query("SELECT * FROM Medication")
    suspend fun getAllMedications(): List<Medication>
}
