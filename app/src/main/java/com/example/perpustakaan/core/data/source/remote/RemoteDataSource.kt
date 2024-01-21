package com.example.perpustakaan.core.data.source.remote

import com.example.perpustakaan.core.data.source.remote.network.ApiConfig
import com.example.perpustakaan.core.data.source.remote.network.ApiService
import com.example.perpustakaan.core.data.source.remote.request.LoginRequest
import com.example.perpustakaan.core.data.source.remote.request.RegisterRequest

class RemoteDataSource(private val api:ApiService) {
    suspend fun login(data:LoginRequest)=api.login(data)
    suspend fun register(data:RegisterRequest)=api.register(data)
}