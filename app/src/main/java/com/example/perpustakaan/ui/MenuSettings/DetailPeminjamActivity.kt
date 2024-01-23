package com.example.perpustakaan.ui.MenuSettings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaan.Adapter.ListDetailDataAdapter
import com.example.perpustakaan.List.Setting
import com.example.perpustakaan.core.data.source.remote.network.State
import com.example.perpustakaan.databinding.ActivityDetailPeminjamBinding
import com.example.perpustakaan.util.Prefs
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toastWarning
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPeminjamActivity : AppCompatActivity() {
    private val viewModel: MenuSettingsViewModel by viewModel()
    private lateinit var binding:ActivityDetailPeminjamBinding
    private lateinit var recyclerViewSettings: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailPeminjamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerViewSettings=binding.recyclerViewListData
        setToolbar(binding.toolBarDetailPeminjam,"Kembali")
        getData()
    }

    private fun getData(){
        val token="Bearer ${Prefs.token}"
        viewModel.getMePeminjam(token).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val idpeminjam =it?.data?.user_id.toString()
                    viewModel.getDataPeminjam(token,idpeminjam).observe(this) {
                        when (it.state) {
                            State.SUCCESS -> {
                                val settingsList = listOf(
                                    Setting("Nama Lengkap", it?.data?.nama_lengkap.toString()),
                                    Setting("Email", it?.data?.email.toString()),
                                    Setting("Phone Number", it?.data?.phone.toString()),
                                    Setting("Alamat", it?.data?.alamat.toString()),
                                )

                                val settingsAdapter = ListDetailDataAdapter(settingsList,this)
                                recyclerViewSettings.layoutManager = LinearLayoutManager(this)
                                recyclerViewSettings.adapter = settingsAdapter
                                Log.e("token",it?.data.toString())
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}