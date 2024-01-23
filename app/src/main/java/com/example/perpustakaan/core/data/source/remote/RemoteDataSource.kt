package com.example.perpustakaan.core.data.source.remote

import com.example.perpustakaan.core.data.source.remote.network.ApiService
import com.example.perpustakaan.core.data.source.remote.request.LoginRequest
import com.example.perpustakaan.core.data.source.remote.request.RegisterRequest
import com.example.perpustakaan.core.data.source.remote.request.ResetPasswordRequest
import com.example.perpustakaan.core.data.source.remote.request.UpdatePeminjamRequest

class RemoteDataSource(private val api:ApiService) {
    suspend fun login(data:LoginRequest)=api.login(data)
    suspend fun register(data:RegisterRequest)=api.register(data)
    suspend fun getMePeminjam(token:String)=api.getMePeminjam(token)
    suspend fun getDataPeminjam(token:String,idPeminjam:String)=api.getDataPeminjam(token,idPeminjam)
    suspend fun updateDataPeminjam(token:String,idPeminjam:String,data:UpdatePeminjamRequest)=api.updateDataPeminjam(token,idPeminjam,data)
    suspend fun resetPasswordPeminjam(token:String,idPeminjam:String,data:ResetPasswordRequest)=api.resetPasswordPeminjam(token,idPeminjam,data)
}