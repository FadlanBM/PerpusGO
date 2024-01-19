package com.example.perpustakaan.retrofit

import retrofit2.http.GET

interface ApiEndpoint {

    @GET("user")
    fun getUser()
}