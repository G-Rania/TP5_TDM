package com.example.exo2.repository

import com.example.exo2.room.PatientDao
import com.example.exo2.entity.Patient

class PatientRepository(private val patientDao: PatientDao) {

    suspend fun addPatient(patient: Patient) = patientDao.insertPatient(patient)

    suspend fun getAllPatients() = patientDao.getAllPatients()

    suspend fun getPatientById(id: Int) = patientDao.getPatientById(id)

}
