package com.example.perpustakaan.core.data.source.remote.network

import com.example.perpustakaan.core.data.source.remote.request.LoginRequest
import com.example.perpustakaan.core.data.source.remote.request.RegisterRequest
import com.example.perpustakaan.core.data.source.remote.request.ResetPasswordRequest
import com.example.perpustakaan.core.data.source.remote.request.UpdatePeminjamRequest
import com.example.perpustakaan.core.data.source.remote.response.LoginResponse
import com.example.perpustakaan.core.data.source.remote.response.MePeminjamResponse
import com.example.perpustakaan.core.data.source.remote.response.PeminjamResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("auth/peminjam")
    suspend fun login(
        @Body login:LoginRequest
    ):Response<LoginResponse>
    @POST("peminjam")
    suspend fun register(
        @Body login:RegisterRequest
    ):Response<ResponseBody>

    @GET("me/peminjam")
    suspend fun getMePeminjam(
        @Header("Authorization") token: String
    ):Response<MePeminjamResponse>

    @GET("peminjam/{peminjamID}")
    suspend fun getDataPeminjam(
        @Header("Authorization") token: String,
        @Path("peminjamID") peminjamID: String
    ):Response<PeminjamResponse>

    @PUT("peminjam/{peminjamID}")
    suspend fun updateDataPeminjam(
        @Header("Authorization") token: String,
        @Path("peminjamID") peminjamID: String,
        @Body login:UpdatePeminjamRequest
    ):Response<ResponseBody>
    @PUT("reset/{peminjamID}")
    suspend fun resetPasswordPeminjam(
        @Header("Authorization") token: String,
        @Path("peminjamID") peminjamID: String,
        @Body login:ResetPasswordRequest
    ):Response<ResponseBody>
    @Multipart
    @PUT("peminjam/updateImage/{peminjamID}")
    suspend fun uploadProfileImage(
        @Header("Authorization") token: String,
        @Path("peminjamID") peminjamID: String?=null,
        @Part login:MultipartBody.Part?=null
    ):Response<ResponseBody>
}