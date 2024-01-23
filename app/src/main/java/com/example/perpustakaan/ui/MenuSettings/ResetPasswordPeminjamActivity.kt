package com.example.perpustakaan.ui.MenuSettings

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.perpustakaan.R
import com.example.perpustakaan.core.data.source.remote.network.State
import com.example.perpustakaan.core.data.source.remote.request.ResetPasswordRequest
import com.example.perpustakaan.core.data.source.remote.request.UpdatePeminjamRequest
import com.example.perpustakaan.databinding.ActivityResetPasswordPeminjamBinding
import com.example.perpustakaan.ui.auth.LoginActivity
import com.example.perpustakaan.ui.main.MainActivity
import com.example.perpustakaan.util.Prefs
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordPeminjamActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResetPasswordPeminjamBinding
    private val viewModel: MenuSettingsViewModel by viewModel()
    private lateinit var tiPasswordOld:EditText
    private lateinit var tiPasswordNew:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResetPasswordPeminjamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolBarResetPass,"Kembali")

        tiPasswordOld=binding.tiPasswordOld
        tiPasswordNew=binding.tiPasswordNew

        binding.btnResetPass.setOnClickListener {
            if (tiPasswordOld.text.isEmpty()){
                tiPasswordOld.setError("Form Password Old masih kosong")
            }
            if (tiPasswordNew.text.isEmpty()){
                tiPasswordNew.setError("Form Password New masih kosong")
            }
            if (binding.tiPasswordNewConfirm.text.toString().isEmpty()){
                binding.tiPasswordNewConfirm.setError("Form Password Confirm masih kosong")
            }
            if (tiPasswordOld.text.isEmpty()||tiPasswordNew.text.isEmpty()||binding.tiPasswordNewConfirm.text.toString().isEmpty()) return@setOnClickListener

            if (tiPasswordNew.text.toString()!=binding.tiPasswordNewConfirm.text.toString()){
                binding.tiPasswordNewConfirm.setError("Form Password Confirm tidak sama")
            }else{
            updateData()
            }
        }
    }

    private fun updateData(){
        val token="Bearer ${Prefs.token}"
        viewModel.getMePeminjam(token).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val body= ResetPasswordRequest(
                        tiPasswordNew.text.toString(),
                        tiPasswordOld.text.toString()
                    )
                    val idpeminjam =it?.data?.user_id.toString()
                    viewModel.resetPasswordPeminjam(token,idpeminjam,body).observe(this) {
                        when (it.state) {
                            State.SUCCESS -> {
                                showSuccessModal()
                            }

                            State.ERROR -> {
                                toastWarning(it?.message.toString())
                            }

                            State.LOADING -> {
                            }
                        }
                    }
                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())
                }

                State.LOADING -> {
                }
            }
        }
    }

    private fun showSuccessModal() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.success_update)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.window?.setBackgroundDrawableResource(R.drawable.background_success)
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.setCancelable(false)

        dialog.show()

        Handler().postDelayed({
            dialog.dismiss()
            showAlertDialogReset("Reset Password","Kamu telah update Password, anda harus login kembali")
        }, 2000)
    }

    fun showAlertDialogReset(ttl:String,msg:String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(ttl)
        alertDialogBuilder.setMessage(msg)

        alertDialogBuilder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            Prefs.token=""
            pushActivity(LoginActivity::class.java)
        })

        alertDialogBuilder.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}