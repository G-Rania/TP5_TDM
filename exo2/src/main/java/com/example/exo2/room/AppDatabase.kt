package com.example.exo2.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.exo2.entity.*


@Database(
    entities = [Patient::class, Medication::class, Prescription::class, PrescriptionMedication::class],
    version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun patientDao() : PatientDao
    abstract fun medicationDao() : MedicationDao
    abstract fun prescriptionDao() : PrescriptionDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun buildDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) { synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java,
                    "prescription_db").fallbackToDestructiveMigration().build()
            }
            }
            return INSTANCE
        }
    }

}

