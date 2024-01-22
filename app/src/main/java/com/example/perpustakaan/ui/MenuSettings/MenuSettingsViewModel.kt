package com.example.perpustakaan.ui.MenuSettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.perpustakaan.core.data.repository.AppRepository
import com.example.perpustakaan.core.data.source.remote.request.RegisterRequest

class MenuSettingsViewModel(val repo: AppRepository): ViewModel() {
    fun getMePeminjam(token:String)=repo.getMePeminjam(token).asLiveData()
    fun getDataPeminjam(token:String,idPeminjam:String)=repo.getDataPeminjam(token,idPeminjam).asLiveData()

}