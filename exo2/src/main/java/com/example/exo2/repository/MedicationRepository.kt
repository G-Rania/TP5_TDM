package com.example.exo2.repository


import com.example.exo2.room.MedicationDao
import com.example.exo2.entity.Medication

class MedicationRepository(private val medicationDao: MedicationDao) {

    suspend fun addMedication(medication: Medication) = medicationDao.insertMedication(medication)

    suspend fun getAllMedications() = medicationDao.getAllMedications()

    suspend fun getMedicationById(id: Int) = medicationDao.getMedicationById(id)
}
