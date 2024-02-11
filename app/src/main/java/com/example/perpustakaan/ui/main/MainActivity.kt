package com.example.perpustakaan.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.example.perpustakaan.core.data.source.remote.network.State
import com.example.perpustakaan.ui.auth.LoginActivity
import com.example.perpustakaan.ui.auth.RegisterActivity
import com.example.perpustakaan.databinding.ActivityMainBinding
import com.example.perpustakaan.ui.MenuActivity
import com.example.perpustakaan.ui.auth.AuthViewModel
import com.example.perpustakaan.util.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    var firebaseAuth= FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) {
            binding.scMain.isVisible=false
            binding.pbLoading.isVisible=true
            getData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.btnRegister.setOnClickListener {
            intentActivity(RegisterActivity::class.java)
        }
        binding.btnLogin.setOnClickListener {
            intentActivity(LoginActivity::class.java)
        }
    }

    private fun getData(){
        val token="Bearer ${Prefs.token}"
        viewModel.getMePeminjam(token).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val idpeminjam =it?.data?.user_id.toString()
                    Prefs.userID=idpeminjam
                    binding.pbLoading.isVisible=false
                    pushActivity(MenuActivity::class.java)
                }

                State.ERROR -> {
                    binding.pbLoading.isVisible=false
                    toastWarning(it?.message.toString())
                }

                State.LOADING -> {
                }
            }
        }
    }
}