package com.example.perpustakaan.ui.MenuSettings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.perpustakaan.core.data.source.remote.network.State
import com.example.perpustakaan.databinding.ActivityDetailPeminjamBinding
import com.example.perpustakaan.util.Prefs
import com.inyongtisto.myhelper.extension.dismisLoading
import com.inyongtisto.myhelper.extension.showLoading
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPeminjamActivity : AppCompatActivity() {
    private val viewModel: MenuSettingsViewModel by viewModel()
    private lateinit var binding:ActivityDetailPeminjamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailPeminjamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
    }

    private fun getData(){
        val token="Bearer ${Prefs.token}"
        viewModel.getMePeminjam(token).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    dismisLoading()
                    Log.e("token",it.data.toString())
                }

                State.ERROR -> {
                    dismisLoading()
                    toastWarning(it?.message.toString())
                }

                State.LOADING -> {
                    showLoading()
                }
            }
        }
    }
}