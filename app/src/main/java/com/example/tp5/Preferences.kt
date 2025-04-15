package com.example.tp5

import android.content.Context
import androidx.core.content.edit

object Preferences {
    private const val PREFS_NAME = "user"

    fun saveUser(context: Context){
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putString("user","1")
        }
    }

    fun getUser(context: Context):String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("user",null)
    }

    fun removeUser(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            remove("user")
        }
    }

}