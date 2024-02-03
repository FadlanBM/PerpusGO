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
import com.example.perpustakaan.ui.MenuSettings.ResetPasswordPeminjamActivity
import com.example.perpustakaan.ui.MenuSettings.UpdateDataActivity
import com.example.perpustakaan.ui.main.MainActivity
import com.example.perpustakaan.util.Prefs
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.loge
import com.inyongtisto.myhelper.extension.pushActivity

class ListSettingAdapter(private val settingsList: List<Setting>,val context: Context):RecyclerView.Adapter<ListSettingAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.textViewSettingTitle)
            val descriptionTextView: TextView = itemView.findViewById(R.id.textViewSettingDescription)
            val aksi:CardView=itemView.findViewById(R.id.btn_aksi_settings)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_settings, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val setting = settingsList[position]
            holder.titleTextView.text = setting.title
            holder.descriptionTextView.text = setting.description
            holder.aksi.setOnClickListener {
                if (setting.title=="Logout"){
                    showAlertDialogLogout("Peringatan","Apakah anda akan Logout ?")
                }
                if (setting.title=="Data Pribadi"){
                    context.intentActivity(DetailPeminjamActivity::class.java)
                }
                if (setting.title=="Ubah Data Pribadi"){
                    context.intentActivity(UpdateDataActivity::class.java)
                }
                if (setting.title=="Reset Password"){
                    context.intentActivity(ResetPasswordPeminjamActivity::class.java)
                }
            }
        }

        override fun getItemCount(): Int {
            return settingsList.size
        }

    fun showAlertDialogLogout(ttl:String,msg:String) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        alertDialogBuilder.setTitle(ttl)

        alertDialogBuilder.setMessage(msg)

        alertDialogBuilder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            Prefs.token=""
            Prefs.userID=""
            context.pushActivity(MainActivity::class.java)
        })

        alertDialogBuilder.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}