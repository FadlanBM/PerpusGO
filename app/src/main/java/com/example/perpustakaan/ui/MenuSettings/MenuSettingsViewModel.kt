package com.example.perpustakaan.ui.MenuSettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.perpustakaan.core.data.repository.AppRepository
import com.example.perpustakaan.core.data.source.remote.request.ResetPasswordRequest
import com.example.perpustakaan.core.data.source.remote.request.UpdatePeminjamRequest

class MenuSettingsViewModel(val repo: AppRepository): ViewModel() {
    fun getMePeminjam(token:String)=repo.getMePeminjam(token).asLiveData()
    fun getDataPeminjam(token:String,idPeminjam:String)=repo.getDataPeminjam(token,idPeminjam).asLiveData()
    fun updateDataPeminjam(token:String,idPeminjam:String,data:UpdatePeminjamRequest)=repo.updateDataPeminjam(token,idPeminjam,data).asLiveData()
    fun resetPasswordPeminjam(token:String,idPeminjam:String,data:ResetPasswordRequest)=repo.resetPasswordPeminjam(token,idPeminjam,data).asLiveData()

}