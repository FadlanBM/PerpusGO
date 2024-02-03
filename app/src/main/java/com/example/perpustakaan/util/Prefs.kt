package com.example.perpustakaan.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.chibatching.kotpref.KotprefModel
import com.example.perpustakaan.core.data.source.models.Peminjam
import com.inyongtisto.myhelper.extension.toJson

object Prefs :KotprefModel() {
    var userID by stringPref("")

    var token by stringPref("")

   /* fun setUser(data: Peminjam?) {
        user = data.toJson()
    }*/
}