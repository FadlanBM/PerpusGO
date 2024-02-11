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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
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

        Prov()
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
            updateData()
        }
    }

    private fun validasiImage(){

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
                    binding.pbUpdateProfile.isVisible=false
                    binding.mainUpdate.isVisible=true
                    showSuccessModal()
                }

                State.ERROR -> {
                    binding.pbUpdateProfile.isVisible=false
                    binding.mainUpdate.isVisible=true
                    toastWarning(it?.message.toString())
                }

                State.LOADING -> {
                    binding.pbUpdateProfile.isVisible=true
                    binding.mainUpdate.isVisible=false
                }
            }
        }
    }
    private fun Prov(){
        viewModel.provinsi().observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val item =ArrayList<String>()
                    val iditem=ArrayList<String>()
                    for (i in 0 until it.data!!.size){
                        val json=it.data[i]
                        val provinsi=json.name
                        val idprovinsi=json.id
                        item.add(provinsi)
                        iditem.add(idprovinsi)
                    }
                    var autoComplite: AutoCompleteTextView =binding.comboBoxListprovinsi
                    var adapter= ArrayAdapter(this,R.layout.list_wilayah,item)

                    autoComplite.setAdapter(adapter)
                    autoComplite.onItemClickListener= AdapterView.OnItemClickListener { adapterView, view, i, l ->
                        iditem.mapIndexed { index, s ->
                            if (i==index){
                                binding.comboBoxListkabupaten.text.clear()
                                binding.comboBoxListkecamatan.text.clear()
                                kab(s)
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
    private fun kab(id:String){
        viewModel.kabupaten(id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.comboBoxListkabupaten.isEnabled=true
                    val item =ArrayList<String>()
                    val iditem=ArrayList<String>()
                    for (i in 0 until it.data!!.size){
                        val json=it.data[i]
                        val kab=json.name
                        val idkab=json.id
                        item.add(kab)
                        iditem.add(idkab)
                    }
                    var autoComplite: AutoCompleteTextView =binding.comboBoxListkabupaten
                    var adapter= ArrayAdapter(this,R.layout.list_wilayah,item)

                    autoComplite.setAdapter(adapter)
                    autoComplite.onItemClickListener= AdapterView.OnItemClickListener { adapterView, view, i, l ->
                        iditem.mapIndexed { index, s ->
                            if (i==index){
                                binding.comboBoxListkecamatan.text.clear()
                                Kec(s)
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
    private fun Kec(id:String){
        viewModel.kecamatan(id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.comboBoxListkecamatan.isEnabled=true
                    val item =ArrayList<String>()
                    for (i in 0 until it.data!!.size){
                        val json=it.data[i]
                        val kab=json.name
                        item.add(kab)
                    }
                    var autoComplite: AutoCompleteTextView =binding.comboBoxListkecamatan
                    var adapter= ArrayAdapter(this,R.layout.list_wilayah,item)

                    autoComplite.setAdapter(adapter)
                    autoComplite.onItemClickListener= AdapterView.OnItemClickListener { adapterView, view, i, l ->
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

    private fun getData(){
        val token="Bearer ${Prefs.token}"
        val idpeminjam =Prefs.userID
        viewModel.getDataPeminjam(token,idpeminjam).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.pbUpdateProfile.isVisible=false
                    binding.mainUpdate.isVisible=true
                    tiNama.setText(it?.data?.nama_lengkap.toString())
                    tiEmail.setText(it?.data?.email.toString())
                    tiAlamat.setText(it?.data?.alamat.toString())
                    tiPhone.setText(it?.data?.phone.toString())
                    Picasso.get().load(Constants.BASE_Image+it?.data?.photo).into(binding.imageProfile)
                    tvInitial.setText(it?.data?.nama_lengkap.toString())
                }

                State.ERROR -> {
                    binding.pbUpdateProfile.isVisible=false
                    binding.mainUpdate.isVisible=true
                    toastWarning(it?.message.toString())
                }

                State.LOADING -> {
                    binding.pbUpdateProfile.isVisible=true
                    binding.mainUpdate.isVisible=false
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
                    if (fileImage!=null){
                        upload()
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
            getData()
            dialog.dismiss()
        }, 2000)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}