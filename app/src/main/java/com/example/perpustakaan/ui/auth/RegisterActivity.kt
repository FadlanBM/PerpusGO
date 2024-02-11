package com.example.perpustakaan.ui.auth

import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.example.perpustakaan.R
import com.example.perpustakaan.core.data.source.remote.network.State
import com.example.perpustakaan.core.data.source.remote.request.LoginRequest
import com.example.perpustakaan.core.data.source.remote.request.RegisterRequest
import com.example.perpustakaan.databinding.ActivityRegisterBinding
import com.example.perpustakaan.ui.MenuActivity
import com.example.perpustakaan.ui.main.MainActivity
import com.example.perpustakaan.util.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.inyongtisto.myhelper.extension.dismisLoading
import com.inyongtisto.myhelper.extension.getErrorBody
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.showLoading
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModel()
    var firebaseAuth= FirebaseAuth.getInstance()
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var tiEmail: EditText
    private lateinit var tiNama: EditText
    private lateinit var tiPassword: EditText
    private lateinit var tiComfirmPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tiEmail=binding.tiEmailRegister
        tiNama=binding.tiNamaRegister
        tiPassword=binding.tiPasswordRegister
        tiComfirmPass=binding.tiConfirmPasswordRegister


        binding.btnRegister.setOnClickListener {
            if (tiPassword.text.isEmpty()){
                tiPassword.error = "Form Password masih kosong"
            }
            if (tiComfirmPass.text.isEmpty()){
                tiComfirmPass.error = "Form Password masih kosong"
            }
            if (tiEmail.text.isEmpty()){
                tiEmail.error="Form Username masih kosong"
            }
            if (tiNama.text.isEmpty()){
                tiNama.error="Form Username masih kosong"
            }
            if (tiPassword.text.isEmpty()||tiEmail.text.isEmpty()||tiComfirmPass.text.isEmpty()||tiNama.text.isEmpty()) return@setOnClickListener

            if (tiPassword.text.toString()!=tiComfirmPass.text.toString()){
                tiComfirmPass.error="Comfirm password tidak sama"
                return@setOnClickListener
            }
            register()
        }

        binding.tvLoginRegister.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun postRegister(uid:String){
              val body= RegisterRequest(
               tiEmail.text.toString(),
               tiNama.text.toString(),
               uid,
           )

           viewModel.register(body).observe(this) {
               when (it.state) {
                   State.SUCCESS -> {
                       Log.e("token",it?.data?.getErrorBody().toString())
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

    private fun register(){
        binding.pbLoading.isVisible=true
        firebaseAuth.createUserWithEmailAndPassword(tiEmail.text.toString(),tiPassword.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val userCreateProfile= userProfileChangeRequest {
                        displayName=tiNama.text.toString()
                    }
                    val user=it.result.user
                    user!!.updateProfile(userCreateProfile)
                        .addOnCompleteListener {
                            firebaseAuth.currentUser?.sendEmailVerification()
                                ?.addOnSuccessListener {
                                    postRegister(user.uid)
                                }
                                ?.addOnFailureListener {
                                    binding.pbLoading.isVisible=false
                                    toastWarning(it.localizedMessage)
                                }
                        }
                        .addOnFailureListener {
                            binding.pbLoading.isVisible=false
                            toastWarning(it.localizedMessage)
                        }
                }
            }
            .addOnFailureListener {
                binding.pbLoading.isVisible=false
                toastWarning(it.localizedMessage)
            }
    }


    private fun showSuccessModal() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.warning_validasi)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.window?.setBackgroundDrawableResource(R.drawable.background_success)
        }
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)
        dialog.show()

        Handler().postDelayed({
            dialog.dismiss()
            pushActivity(LoginActivity::class.java)
        }, 3000)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        pushActivity(MainActivity::class.java)
    }
}