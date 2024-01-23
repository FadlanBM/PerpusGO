package com.example.perpustakaan.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.perpustakaan.List.Setting
import com.example.perpustakaan.R
import com.example.perpustakaan.ui.MenuSettings.DetailPeminjamActivity
import com.example.perpustakaan.ui.main.MainActivity
import com.example.perpustakaan.util.Prefs
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.loge
import com.inyongtisto.myhelper.extension.pushActivity

class ListDetailDataAdapter(private val settingsList: List<Setting>, val context: Context):RecyclerView.Adapter<ListDetailDataAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.tvJudul_listData)
            val descriptionTextView: TextView = itemView.findViewById(R.id.tvName_listData)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_detail_data, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val setting = settingsList[position]
            holder.titleTextView.text = setting.title
            holder.descriptionTextView.text = setting.description
        }

        override fun getItemCount(): Int {
            return settingsList.size
        }
}