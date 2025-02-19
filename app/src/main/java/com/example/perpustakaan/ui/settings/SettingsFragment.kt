package com.example.perpustakaan.ui.settings

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaan.Adapter.ListSettingAdapter
import com.example.perpustakaan.List.Setting
import com.example.perpustakaan.core.data.source.remote.network.State
import com.example.perpustakaan.databinding.FragmentSettingsBinding
import com.example.perpustakaan.ui.MenuSettings.MenuSettingsViewModel
import com.example.perpustakaan.util.Constants
import com.example.perpustakaan.util.Prefs
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import com.inyongtisto.myhelper.extension.dismisLoading
import com.inyongtisto.myhelper.extension.getInitial
import com.inyongtisto.myhelper.extension.showLoading
import com.inyongtisto.myhelper.extension.toastWarning
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException


class SettingsFragment : Fragment() {
    private val viewModel: SettingsViewModel by viewModel()

    private var _binding: FragmentSettingsBinding? = null
    private lateinit var recyclerViewSettings: RecyclerView
    private lateinit var tvName:TextView
    private lateinit var tvEmail:TextView
    private lateinit var tvphone:TextView
    private lateinit var tvInitial:TextView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerViewSettings = binding.recyclerViewSettings
        tvName=binding.tvName
        tvEmail=binding.tvEmail
        tvphone=binding.tvPhone
        tvInitial=binding.tvInisial
        getData()

        val settingsList = listOf(
            Setting("Data Pribadi", "Melihat Data Pribadi"),
            Setting("Ubah Data Pribadi", "Edit Data Pribadi"),
            Setting("Reset Password", "Reset Password"),
            Setting("Logout", "Keluar Dari Akun"),
        )

        val settingsAdapter = ListSettingAdapter(settingsList,requireContext())
        recyclerViewSettings.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewSettings.adapter = settingsAdapter

        return root
    }

    override fun onResume() {
        getData()
        super.onResume()
    }

    private fun getData(){
        val token="Bearer ${Prefs.token}"
        val idpeminjam =Prefs.userID
        Log.e("idpeminjam",idpeminjam)
        viewModel.getDataPeminjam(token,idpeminjam).observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    tvName.text=it?.data?.nama_lengkap.toString()
                    tvphone.text=it?.data?.phone.toString()
                    tvEmail.text=it?.data?.email.toString()
/*
                    Log.e("iamge",Constants.BASE_URL+it?.data?.photo.toString())
*/
/*
                    Picasso.get().load(Constants.BASE_Image+it?.data?.photo).into(binding.imageProfile)
*/
                    tvInitial.text=it?.data?.nama_lengkap.toString()
                }
                State.ERROR -> {
                    toastWarning(it?.message.toString())
                }
                State.LOADING -> {
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}