package com.example.perpustakaan.ui.MenuSettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.perpustakaan.core.data.repository.AppRepository
import com.example.perpustakaan.core.data.source.remote.request.ResetPasswordRequest
import com.example.perpustakaan.core.data.source.remote.request.UpdatePeminjamRequest
import okhttp3.MultipartBody

class MenuSettingsViewModel(val repo: AppRepository): ViewModel() {
    fun getDataPeminjam(token:String,idPeminjam:String)=repo.getDataPeminjam(token,idPeminjam).asLiveData()
    fun updateDataPeminjam(token:String,idPeminjam:String,data:UpdatePeminjamRequest)=repo.updateDataPeminjam(token,idPeminjam,data).asLiveData()
    fun uploadProfileImage(token:String,idPeminjam:String,image: MultipartBody.Part?=null)=repo.uploadProfileImage(token,idPeminjam,image).asLiveData()
    fun provinsi()=repo.provinsi().asLiveData()
    fun kabupaten(id:String)=repo.kabupaten(id).asLiveData()
    fun kecamatan(id:String)=repo.kecamatan(id).asLiveData()
}
