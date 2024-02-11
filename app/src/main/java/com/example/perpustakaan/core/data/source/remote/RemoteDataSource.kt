package com.example.perpustakaan.core.data.source.remote

import com.example.perpustakaan.core.data.source.remote.network.ApiService
import com.example.perpustakaan.core.data.source.remote.network.ApiWilayah
import com.example.perpustakaan.core.data.source.remote.request.LoginRequest
import com.example.perpustakaan.core.data.source.remote.request.RegisterRequest
import com.example.perpustakaan.core.data.source.remote.request.ResetPasswordRequest
import com.example.perpustakaan.core.data.source.remote.request.UpdatePeminjamRequest
import okhttp3.MultipartBody
import retrofit2.http.Multipart

class RemoteDataSource(private val api:ApiService,private val wilayah:ApiWilayah) {
    suspend fun login(data:LoginRequest)=api.login(data)
    suspend fun register(data:RegisterRequest)=api.register(data)
    suspend fun getMePeminjam(token:String)=api.getMePeminjam(token)
    suspend fun getDataPeminjam(token:String,idPeminjam:String)=api.getDataPeminjam(token,idPeminjam)
    suspend fun updateDataPeminjam(token:String,idPeminjam:String,data:UpdatePeminjamRequest)=api.updateDataPeminjam(token,idPeminjam,data)
    suspend fun uploadProfileImage(token:String,idPeminjam:String,image:MultipartBody.Part?=null)=api.uploadProfileImage(token,idPeminjam,image)
    suspend fun provinsi()=wilayah.provinsi()
    suspend fun kabupaten(id:String)=wilayah.kabupaten(id)
    suspend fun kecamatan(id:String)=wilayah.kecamatan(id)
}