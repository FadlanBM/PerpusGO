package com.example.perpustakaan.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.perpustakaan.core.data.repository.AppRepository
import com.example.perpustakaan.core.data.source.remote.request.LoginRequest
import com.example.perpustakaan.core.data.source.remote.request.RegisterRequest

class AuthViewModel(val repo:AppRepository):ViewModel() {
    fun login(data:LoginRequest)=repo.login(data).asLiveData()
    fun register(data:RegisterRequest)=repo.register(data).asLiveData()

    fun getMePeminjam(token:String)=repo.getMePeminjam(token).asLiveData()

}