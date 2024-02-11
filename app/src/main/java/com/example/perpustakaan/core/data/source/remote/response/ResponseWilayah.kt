package com.example.perpustakaan.core.data.source.remote.response

data class ResponseProv(
    val id: String,
    val name: String
)
data class ResponseKab(
    val id: String,
    val name: String,
    val province_id: String
)
data class ResponseKec(
    val id: String,
    val name: String,
    val regency_id: String
)
