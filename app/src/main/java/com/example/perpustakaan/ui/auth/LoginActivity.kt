package com.example.perpustakaan.ui.auth

import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.perpustakaan.R
import com.example.perpustakaan.core.data.source.remote.network.State
import com.example.perpustakaan.core.data.source.remote.request.LoginRequest
import com.example.perpustakaan.databinding.ActivityLoginBinding
import com.example.perpustakaan.ui.MenuActivity
import com.example.perpustakaan.ui.main.MainActivity
import com.example.perpustakaan.util.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.inyongtisto.myhelper.extension.dismisLoading
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.logm
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.showLoading
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var tiPassword:EditText
    private lateinit var tiEmail:EditText
    var firebaseAuth=FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tiPassword=binding.tiPasswordLogin
        tiEmail=binding.tiEmailLogin

        binding.tvRegisterLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            if (tiPassword.text.isEmpty()){
                tiPassword.error = "Form Password masih kosong"
            }
            if (tiEmail.text.isEmpty()){
                tiEmail.error="Form Username masih kosong"
            }
            if (tiPassword.text.isEmpty()||tiEmail.text.isEmpty()) return@setOnClickListener

            login()
        }
    }

    private fun login(){
        binding.pbLoading.isVisible=true
        firebaseAuth.signInWithEmailAndPassword(tiEmail.text.toString(),tiPassword.text.toString())
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    val verif=firebaseAuth.currentUser?.isEmailVerified
                    if (verif==true){
                        postLogin(firebaseAuth.uid.toString())
                    }else{
                        binding.pbLoading.isVisible=false
                        showValidasiModal("Email Validasi")
                    }
                }else{
                    toastWarning("Email/Password salah ")
                    binding.pbLoading.isVisible=false
                }
            }
    }

    private fun postLogin(uid:String){
        val body=LoginRequest(
            uid
        )
        Log.e("postLogin",uid)
        viewModel.login(body).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val token=it?.data
                    Prefs.token=token.toString()
                    getData()

                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())
                    binding.pbLoading.isVisible=false

                }

                State.LOADING -> {
                }
            }
        }
    }

    private fun getData(){
        val token="Bearer ${Prefs.token}"
        Log.e("token",token)
        viewModel.getMePeminjam(token).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val idpeminjam =it?.data?.user_id.toString()
                    Prefs.userID=idpeminjam
                    showSuccessModal()
                    binding.pbLoading.isVisible=false
                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())
                    binding.pbLoading.isVisible=false
                }
                State.LOADING -> {
                }
            }
        }
    }

    private fun showSuccessModal() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.success_login)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.window?.setBackgroundDrawableResource(R.drawable.background_success)
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.setCancelable(false)

        dialog.show()

        Handler().postDelayed({
            dialog.dismiss()
            pushActivity(MenuActivity::class.java)
        }, 2000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        pushActivity(MainActivity::class.java)
    }

    private fun showValidasiModal(msg:String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.warning_validasi)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.window?.setBackgroundDrawableResource(R.drawable.background_success)
        }
        dialog.findViewById<TextView>(R.id.textSuccess_validasi).text=msg
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)
        dialog.show()

        Handler().postDelayed({
            dialog.dismiss()
        }, 3000)
    }
}