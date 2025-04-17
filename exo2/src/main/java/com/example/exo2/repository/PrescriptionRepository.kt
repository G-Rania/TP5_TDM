package com.example.exo2.repository

import com.example.exo2.room.PrescriptionDao
import com.example.exo2.entity.Prescription
import com.example.exo2.entity.PrescriptionMedication
import com.example.exo2.entity.PrescriptionWithMedications

class PrescriptionRepository(private val prescriptionDao: PrescriptionDao) {

    suspend fun addPrescription(prescription: Prescription) = prescriptionDao.insertPrescription(prescription)

    suspend fun getAllPrescriptions() = prescriptionDao.getAllPrescriptionsWithMedications()

    suspend fun addMedicationToPrescription(link: PrescriptionMedication) =
        prescriptionDao.insertPrescriptionMedicationCrossRef(link)

    suspend fun getPrescriptionById(id : Int) =
        prescriptionDao.getPrescriptionById(id)

    suspend fun getPrescriptionWithMedications(id: Int) =
        prescriptionDao.getPrescriptionWithMedications(id)

    suspend fun addPrescriptionWithReturn(prescription: Prescription): Long {
        return prescriptionDao.insertPrescription(prescription)
    }

}
