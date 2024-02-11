package com.example.perpustakaan.core.data.source.remote.network

import com.example.perpustakaan.core.data.source.remote.request.LoginRequest
import com.example.perpustakaan.core.data.source.remote.request.RegisterRequest
import com.example.perpustakaan.core.data.source.remote.request.UpdatePeminjamRequest
import com.example.perpustakaan.core.data.source.remote.response.LoginResponse
import com.example.perpustakaan.core.data.source.remote.response.MePeminjamResponse
import com.example.perpustakaan.core.data.source.remote.response.PeminjamResponse
import com.example.perpustakaan.core.data.source.remote.response.ResponseKab
import com.example.perpustakaan.core.data.source.remote.response.ResponseKec
import com.example.perpustakaan.core.data.source.remote.response.ResponseProv
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
    @POST("auth/login/peminjam")
    suspend fun login(
        @Body login:LoginRequest
    ):Response<LoginResponse>
    @POST("auth/register/peminjam")
    suspend fun register(
        @Body login:RegisterRequest
    ):Response<ResponseBody>

    @GET("auth/me/peminjam")
    suspend fun getMePeminjam(
        @Header("Authorization") token: String
    ):Response<MePeminjamResponse>

    @GET("profile/peminjam/{peminjamID}")
    suspend fun getDataPeminjam(
        @Header("Authorization") token: String,
        @Path("peminjamID") peminjamID: String
    ):Response<PeminjamResponse>
    @PUT("profile/peminjam/{peminjamID}")
    suspend fun updateDataPeminjam(
        @Header("Authorization") token: String,
        @Path("peminjamID") peminjamID: String,
        @Body login:UpdatePeminjamRequest
    ):Response<ResponseBody>
    @Multipart
    @PUT("profile/peminjam/image/{id}")
    suspend fun uploadProfileImage(
        @Header("Authorization") token: String,
        @Path("id") peminjamID: String?=null,
        @Part login:MultipartBody.Part?=null
    ):Response<ResponseBody>
}

interface ApiWilayah {
    @GET("provinces.json")
    suspend fun provinsi(
    ):Response<List<ResponseProv>>
    @GET("regencies/{id}.json")
    suspend fun kabupaten(
        @Path("id") id: String
    ):Response<List<ResponseKab>>
    @GET("districts/{id}.json")
    suspend fun kecamatan(
        @Path("id") id: String
    ):Response<List<ResponseKec>>
}