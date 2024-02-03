package com.example.perpustakaan.ui.MenuSettings

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.perpustakaan.R
import com.example.perpustakaan.core.data.source.remote.network.State
import com.example.perpustakaan.core.data.source.remote.request.UpdatePeminjamRequest
import com.example.perpustakaan.databinding.ActivityUpdateDataBinding
import com.example.perpustakaan.ui.MenuActivity
import com.example.perpustakaan.util.Constants
import com.example.perpustakaan.util.Prefs
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toMultipartBody
import com.inyongtisto.myhelper.extension.toastWarning
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException

class UpdateDataActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUpdateDataBinding
    private val viewModel: MenuSettingsViewModel by viewModel()
    private lateinit var tiEmail: EditText
    private lateinit var tiNama: EditText
    private lateinit var tiAlamat: EditText
    private lateinit var tiPhone: EditText
    private lateinit var tvInitial: TextView
    private var fileImage:File?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolBarUpdatePeminjam,"Kembali")

        tiEmail=binding.tiEmailUpdate
        tiNama=binding.tiNamaUpdate
        tiPhone=binding.tiPhoneUpdate
        tiAlamat=binding.tiAlamatUpdate
        tvInitial=binding.tvInisial

        getData()
        handlerButton()

        binding.btnRegister.setOnClickListener {
            if (tiNama.text.isEmpty()){
                tiNama.setError("Form Nama masih kosong")
            }
            if (tiEmail.text.isEmpty()){
                tiEmail.setError("Form Email masih kosong")
            }
            if (tiPhone.text.isEmpty()){
                tiPhone.setError("Form Phone masih kosong")
            }
            if (tiAlamat.text.isEmpty()){
                tiAlamat.setError("Form Alamat masih kosong")
            }
            if (tiNama.text.isEmpty()||tiEmail.text.isEmpty()||tiPhone.text.isEmpty()||tiAlamat.text.isEmpty()) return@setOnClickListener
            validasiImage()
            updateData()
        }
    }

    private fun validasiImage(){
        if (fileImage!=null){
            upload()
        }
    }

    private fun handlerButton() {
        binding.imageProfile.setOnClickListener {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        ImagePicker.Companion.with(this)
            .provider(ImageProvider.BOTH) //Or bothCameraGallery()
            .crop()
            .cropOval()
            .createIntentFromDialog { launcher.launch(it) }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                uri?.let { galleryUri ->
                    fileImage=File(uri.path ?:"")
                    Picasso.get().load(galleryUri).into(binding.imageProfile)
                }
            }
        }

    private fun upload(){
        val token="Bearer ${Prefs.token}"
        val file=fileImage.toMultipartBody()
        val id_user=Prefs.userID
        viewModel.uploadProfileImage(token,id_user,file).observe(this) {
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


    private fun getData(){
        val token="Bearer ${Prefs.token}"
        val idpeminjam =Prefs.userID
        viewModel.getDataPeminjam(token,idpeminjam).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    tiNama.setText(it?.data?.nama_lengkap.toString())
                    tiEmail.setText(it?.data?.email.toString())
                    tiAlamat.setText(it?.data?.alamat.toString())
                    tiPhone.setText(it?.data?.phone.toString())
                    Picasso.get().load(Constants.BASE_Image+it?.data?.photo).into(binding.imageProfile)
                    tvInitial.setText(it?.data?.nama_lengkap.toString())
                }

                State.ERROR -> {
                    toastWarning(it?.message.toString())
                }

                State.LOADING -> {
                }
            }
        }
    }

    private fun updateData(){
        val token="Bearer ${Prefs.token}"
        val body=UpdatePeminjamRequest(
            tiAlamat.text.toString(),
            tiEmail.text.toString(),
            tiNama.text.toString(),
            tiPhone.text.toString()
        )
        val idpeminjam =Prefs.userID
        viewModel.updateDataPeminjam(token,idpeminjam,body).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    getData()
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
            getData()
        }, 2000)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}