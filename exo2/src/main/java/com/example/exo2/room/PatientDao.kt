package com.example.exo2.room

import androidx.room.*
import com.example.exo2.entity.Patient

@Dao
interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: Patient): Long

    @Query("SELECT * FROM Patient WHERE patientId = :id")
    suspend fun getPatientById(id: Int): Patient

    @Query("SELECT * FROM Patient")
    suspend fun getAllPatients(): List<Patient>
}
