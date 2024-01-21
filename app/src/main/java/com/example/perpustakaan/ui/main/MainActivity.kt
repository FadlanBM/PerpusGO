package com.example.perpustakaan.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.perpustakaan.ui.auth.LoginActivity
import com.example.perpustakaan.ui.auth.RegisterActivity
import com.example.perpustakaan.databinding.ActivityMainBinding
import com.example.perpustakaan.ui.MenuActivity
import com.example.perpustakaan.util.Prefs
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.pushActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        validasiLogin()
        binding.btnRegister.setOnClickListener {
            intentActivity(RegisterActivity::class.java)
        }
        binding.btnLogin.setOnClickListener {
            intentActivity(LoginActivity::class.java)
        }
    }

    private fun validasiLogin(){
        val token=Prefs.token
        if (token.isNotEmpty()) pushActivity(MenuActivity::class.java)
    }
}