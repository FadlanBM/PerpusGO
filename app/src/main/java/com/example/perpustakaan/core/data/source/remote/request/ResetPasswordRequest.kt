package com.example.perpustakaan.core.data.source.remote.request

data class ResetPasswordRequest(
    val password_new: String,
    val password_old: String
)