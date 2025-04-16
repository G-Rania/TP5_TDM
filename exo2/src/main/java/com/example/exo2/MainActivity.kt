package com.example.exo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.exo2.ui.theme.TP5Theme
import com.example.exo2.screen.PrescriptionScreen
import com.example.exo2.viewmodel.MedicationViewModel
import com.example.exo2.viewmodel.PatientViewModel
import com.example.exo2.viewmodel.PrescriptionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TP5Theme{
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    val application = context.applicationContext as MyApplication

    val patientViewModel = PatientViewModel(application.patientRepository)
    val medicationViewModel = MedicationViewModel(application.medicationRepository)
    val prescriptionViewModel = PrescriptionViewModel(application.prescriptionRepository)

    PrescriptionScreen(
        patientViewModel = patientViewModel,
        medicationViewModel = medicationViewModel,
        prescriptionViewModel = prescriptionViewModel
    )
}
