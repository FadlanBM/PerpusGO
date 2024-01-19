package com.example.perpustakaan.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServices {

    val BASE_URL:String="http://127.0.0.1:8080/api/"
    val endpoint:ApiEndpoint
        get() {
            val retrofit=Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return  retrofit.create(ApiEndpoint::class.java)
        }
}