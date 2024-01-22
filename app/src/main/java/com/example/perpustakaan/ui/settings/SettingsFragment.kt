package com.example.perpustakaan.ui.settings

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaan.Adapter.ListSettingAdapter
import com.example.perpustakaan.List.Setting
import com.example.perpustakaan.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private lateinit var recyclerViewSettings: RecyclerView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerViewSettings = binding.recyclerViewSettings


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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}