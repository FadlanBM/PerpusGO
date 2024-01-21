package com.example.perpustakaan.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.chibatching.kotpref.KotprefModel

object Prefs :KotprefModel() {
    var token by stringPref("")
}