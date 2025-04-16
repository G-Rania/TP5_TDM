package com.example.exo2.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exo2.entity.Patient
import com.example.exo2.repository.PatientRepository
import kotlinx.coroutines.launch

class PatientViewModel(private val patientRepository: PatientRepository) : ViewModel() {

    val patients = mutableStateOf(emptyList<Patient>())
    val selectedPatient = mutableStateOf<Patient?>(null)

    fun getPatients() {
        viewModelScope.launch {
            patients.value = patientRepository.getAllPatients()
        }
    }

    fun getPatientById(id : Int){
        viewModelScope.launch {
            selectedPatient.value = patientRepository.getPatientById(id)
        }
    }

    fun addPatient(patient: Patient) {
        viewModelScope.launch {
            patientRepository.addPatient(patient)
            getPatients()
        }
    }

}
