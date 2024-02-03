package com.example.perpustakaan.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.perpustakaan.core.data.repository.AppRepository

class SettingsViewModel(val repo: AppRepository): ViewModel() {
    fun getDataPeminjam(token:String,idPeminjam:String)=repo.getDataPeminjam(token,idPeminjam).asLiveData()
}