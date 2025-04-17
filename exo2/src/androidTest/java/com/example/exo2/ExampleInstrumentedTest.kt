package com.example.exo2

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.exo2.entity.Medication
import com.example.exo2.entity.Patient
import com.example.exo2.entity.Prescription
import com.example.exo2.entity.PrescriptionMedication
import com.example.exo2.room.AppDatabase
import com.example.exo2.room.MedicationDao
import com.example.exo2.room.PatientDao
import com.example.exo2.room.PrescriptionDao
import kotlinx.coroutines.flow.first

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import java.util.Date

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var prescriptionDao: PrescriptionDao
    private lateinit var patientDao: PatientDao
    private lateinit var medicationDao: MedicationDao
    private lateinit var db: AppDatabase

    @Before
    fun initDB() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            appContext, AppDatabase::class.java
        ).allowMainThreadQueries().build()

        patientDao = db.patientDao()
        prescriptionDao = db.prescriptionDao()
        medicationDao = db.medicationDao()
    }

    @After
    fun closeDB() {
        db.close()
    }


    @Test
    fun testInsertAndGetUser() {
        val patient1 =
            Patient(patientId = 1,firstName = "rania", lastName = "gueddouche")
        runBlocking {
            patientDao.insertPatient(patient1)
            val list = patientDao.getAllPatients()
            assertEquals(patient1, list[0])
        }

    }

    @Test
    fun insertAndGetPrescription() = runBlocking {
        val patient1 = Patient(patientId = 1,firstName = "rania", lastName = "gueddouche")
        patientDao.insertPatient(patient1)
        val prescription = Prescription(
            prescriptionId = 1,
            patientId = 1,
            date = Date(),
        )

        // Insertion dans la base de données
        val prescriptionId = prescriptionDao.insertPrescription(prescription).toInt()

        // Récupération de la prescription
        val loadedPrescription = prescriptionDao.getPrescriptionById(prescriptionId)

        // Vérification
        assertNotNull(loadedPrescription)
        assertEquals(prescription.patientId, loadedPrescription?.patientId)
        assertEquals(prescription.patientId, loadedPrescription?.patientId)
    }

    @Test
    fun insertAndGetMedication() = runBlocking {
        val medication = Medication(
            medicationId = 1,
            name = "Paracétamol",
            dosage = "500mg",
            instructions = "À prendre avec de l'eau"
        )

        // Insertion dans la base de données
        val medicationId = medicationDao.insertMedication(medication).toInt()

        // Récupération du médicament
        val loadedMedication = medicationDao.getMedicationById(medicationId)

        // Vérification
        assertNotNull(loadedMedication)
        assertEquals(medication.name, loadedMedication?.name)
        assertEquals(medication.dosage, loadedMedication?.dosage)
        assertEquals(medication.instructions, loadedMedication?.instructions)
    }

    @Test
    fun linkMedicationToPrescription() = runBlocking {
        val patient1 = Patient(patientId = 1,firstName = "rania", lastName = "gueddouche")
        patientDao.insertPatient(patient1)

        val prescription = Prescription(
            prescriptionId = 1,
            patientId = 1,
            date = Date(),
        )

        val medication = Medication(
            medicationId = 2,
            name = "Amoxicilline",
            dosage = "1g",
            instructions = "À prendre pendant les repas"
        )

        // Insertion dans la base de données
        val prescriptionId = prescriptionDao.insertPrescription(prescription).toInt()
        val medicationId = medicationDao.insertMedication(medication).toInt()

        // Création du lien
        val link = PrescriptionMedication(
            prescriptionId = prescriptionId,
            medicationId = medicationId,
        )

        prescriptionDao.insertPrescriptionMedicationCrossRef(link)

        val medicationLinks = prescriptionDao.getPrescriptionWithMedications(prescriptionId)

        // verification
        assertEquals(1, medicationLinks.medications.size)
        assertEquals(prescriptionId, medicationLinks.prescription.prescriptionId)
        assertEquals(medicationId, medicationLinks.medications[0].medicationId)
        assertEquals("Amoxicilline", medicationLinks.medications[0].name)
    }
}