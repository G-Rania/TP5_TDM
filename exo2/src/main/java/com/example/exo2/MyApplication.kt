package com.example.exo2

import android.app.Application
import com.example.exo2.repository.*
import com.example.exo2.room.AppDatabase
import com.example.exo2.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    // Database instance with lazy initialization
    private val database by lazy { AppDatabase.buildDatabase(this) }

    // DAOs with lazy initialization
    private val patientDao by lazy { database?.patientDao() }
    private val medicationDao by lazy { database?.medicationDao() }
    private val prescriptionDao by lazy { database?.prescriptionDao() }

    // Repositories
    val patientRepository by lazy { PatientRepository(patientDao!!) }
    val medicationRepository by lazy { MedicationRepository(medicationDao!!) }
    val prescriptionRepository by lazy { PrescriptionRepository(prescriptionDao!!) }

    override fun onCreate() {
        super.onCreate()

        // Launch Coroutine to insert test data
        applicationScope.launch {
            insertTestData()
        }
    }

    private suspend fun insertTestData() {
        // Explicitly check if DAOs are properly initialized
        val patientDaoInstance = patientDao
        val medicationDaoInstance = medicationDao

        if (patientDaoInstance != null && medicationDaoInstance != null) {
            // Ensure data is only inserted if tables are empty
            val existingPatients = patientDaoInstance.getAllPatients()
            val existingMeds = medicationDaoInstance.getAllMedications()

            if (existingPatients.isEmpty() && existingMeds.isEmpty()) {
                // Insert test patients
                patientDaoInstance.insertPatient(Patient(firstName = "Yasmine", lastName = "Benali"))
                patientDaoInstance.insertPatient(Patient(firstName = "Rayan", lastName = "Haddad"))

                // Insert test medications
                medicationDaoInstance.insertMedication(
                    Medication(name = "Doliprane", dosage = "500mg", instructions = "1 comprimé toutes les 6 heures")
                )
                medicationDaoInstance.insertMedication(
                    Medication(name = "Ibuprofène", dosage = "200mg", instructions = "Après les repas, 3x par jour")
                )
                medicationDaoInstance.insertMedication(
                    Medication(name = "Amoxicilline", dosage = "1g", instructions = "Matin et soir pendant 7 jours")
                )
            }
        }
    }
}
