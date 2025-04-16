package com.example.exo2.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exo2.entity.Medication
import com.example.exo2.entity.Patient
import com.example.exo2.viewmodel.MedicationViewModel
import com.example.exo2.viewmodel.PatientViewModel
import com.example.exo2.viewmodel.PrescriptionViewModel
import com.example.exo2.ui.theme.TP5Theme

@Composable
fun PrescriptionScreen(
    patientViewModel: PatientViewModel,
    medicationViewModel: MedicationViewModel,
    prescriptionViewModel: PrescriptionViewModel
) {
    var selectedPatient by remember { mutableStateOf<Patient?>(null) }
    var selectedMedications by remember { mutableStateOf<List<Medication>>(emptyList()) }
    var prescriptionDate by remember { mutableStateOf(TextFieldValue("")) }

    val patients = patientViewModel.patients.value
    val medications = medicationViewModel.medications.value

    // Loading patients and medications
    LaunchedEffect(Unit) {
        patientViewModel.getPatients()
        medicationViewModel.getMedications()
    }

    // Main UI
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Créer une Ordonnance", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Patient selection
        DropdownMenuField(
            label = "Sélectionner un Patient",
            options = patients.map { it.firstName + " " + it.lastName },
            selectedOption = selectedPatient?.let { it.firstName + " " + it.lastName } ?: "",
            onOptionSelected = { option ->
                selectedPatient = patients.find { it.firstName + " " + it.lastName == option }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Medications selection
        MedicationSelection(
            medications = medications,
            selectedMedications = selectedMedications,
            onMedicationSelected = { medication ->
                selectedMedications = selectedMedications.toMutableList().apply { add(medication) }
            },
            onMedicationRemoved = { medication ->
                selectedMedications = selectedMedications.toMutableList().apply { remove(medication) }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Prescription date
        BasicTextField(
            value = prescriptionDate,
            onValueChange = { prescriptionDate = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Date de l'ordonnance : ")
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save prescription button
        Button(
            onClick = {
                selectedPatient?.let { patient ->
                    prescriptionViewModel.createPrescription(
                        patient = patient,
                        medications = selectedMedications,
                        date = prescriptionDate.text
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Créer l'Ordonnance")
        }
    }
}

// Medication selection UI
@Composable
fun MedicationSelection(
    medications: List<Medication>,
    selectedMedications: List<Medication>,
    onMedicationSelected: (Medication) -> Unit,
    onMedicationRemoved: (Medication) -> Unit
) {
    Column {
        Text(text = "Sélectionner les Médicaments", style = MaterialTheme.typography.titleLarge)

        medications.forEach { medication ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedMedications.contains(medication),
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            onMedicationSelected(medication)
                        } else {
                            onMedicationRemoved(medication)
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = medication.name)
            }
        }
    }
}

// Dropdown menu UI
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { expanded = true } // Déplacement ici
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.small
                )
                .padding(12.dp)
        ) {
            Text(text = selectedOption.ifEmpty { "Veuillez sélectionner" })
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

