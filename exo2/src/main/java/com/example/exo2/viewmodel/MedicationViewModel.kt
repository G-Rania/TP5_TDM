package com.example.exo2.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exo2.entity.Medication
import com.example.exo2.entity.Patient
import com.example.exo2.repository.MedicationRepository
import kotlinx.coroutines.launch

class MedicationViewModel(private val medicationRepository: MedicationRepository) : ViewModel() {

    val medications = mutableStateOf(emptyList<Medication>())
    val selectedMedication = mutableStateOf<Medication?>(null)


    fun getMedications() {
        viewModelScope.launch {
            medications.value = medicationRepository.getAllMedications()
        }
    }

    fun getMedicationById(id : Int){
        viewModelScope.launch {
            selectedMedication.value = medicationRepository.getMedicationById(id)
        }
    }

    fun addMedication(medication: Medication) {
        viewModelScope.launch {
            medicationRepository.addMedication(medication)
            getMedications()
        }
    }

}
