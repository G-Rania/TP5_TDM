package com.example.exo2.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exo2.entity.Prescription
import com.example.exo2.entity.PrescriptionMedication
import com.example.exo2.entity.PrescriptionWithMedications
import com.example.exo2.repository.PrescriptionRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class PrescriptionViewModel(private val prescriptionRepository: PrescriptionRepository) : ViewModel() {

    val prescriptions = mutableStateOf(emptyList<PrescriptionWithMedications>())
    val prescriptionWithMeds = mutableStateOf<PrescriptionWithMedications?>(null)

    fun getPrescriptions() {
        viewModelScope.launch {
            prescriptions.value = prescriptionRepository.getAllPrescriptions()
        }
    }

    fun addPrescription(prescription: Prescription) {
        viewModelScope.launch {
            prescriptionRepository.addPrescription(prescription)
            getPrescriptions()
        }
    }

    fun createPrescription(
        patient: com.example.exo2.entity.Patient,
        medications: List<com.example.exo2.entity.Medication>,
        date: String
    ) {
        viewModelScope.launch {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = formatter.parse(date)

            val prescription = Prescription(
                patientId = patient.patientId,
                date = parsedDate
            )

            val newId = prescriptionRepository.addPrescriptionWithReturn(prescription)
            val prescriptionId = newId?.toInt() ?: return@launch

            medications.forEach { med ->
                val medId = med.medicationId ?: return@forEach
                prescriptionRepository.addMedicationToPrescription(
                    PrescriptionMedication(prescriptionId, medId)
                )
            }

            getPrescriptions()
        }
    }



    fun linkMedicationToPrescription(prescriptionId: Int, medicationId: Int) {
        viewModelScope.launch {
            prescriptionRepository.addMedicationToPrescription(
                PrescriptionMedication(prescriptionId, medicationId)
            )
        }
    }

    fun getPrescriptionWithMedications(prescriptionId: Int) {
        viewModelScope.launch {
            prescriptionWithMeds.value = prescriptionRepository.getPrescriptionWithMedications(prescriptionId)
        }
    }
}
