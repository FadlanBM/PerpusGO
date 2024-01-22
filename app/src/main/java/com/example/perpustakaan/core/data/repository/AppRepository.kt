package com.example.perpustakaan.core.data.repository

import android.util.Log
import com.example.perpustakaan.core.data.source.local.LocalDataSource
import com.example.perpustakaan.core.data.source.remote.RemoteDataSource
import com.example.perpustakaan.core.data.source.remote.network.Resource
import com.example.perpustakaan.core.data.source.remote.request.LoginRequest
import com.example.perpustakaan.core.data.source.remote.request.RegisterRequest
import com.inyongtisto.myhelper.extension.getErrorBody
import kotlinx.coroutines.flow.flow

class AppRepository(val local:LocalDataSource,val remote:RemoteDataSource) {
    fun login(data:LoginRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.login(data).let {
                if (it.isSuccessful){
                    val body=it.body()
                    emit(Resource.success(body?.token))
                    Log.d("Success",body.toString())
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi Kesalahan",null))
                    Log.e("ERROR","Error Http")
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?:"terjadi Kesalahan",null))
            Log.e("TAG","Login Error" + e.message)
        }
    }
    fun register(data:RegisterRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.register(data).let {
                if (it.isSuccessful){
                    emit(Resource.success(it))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi Kesalahan",null))
                    Log.e("ERROR","Error Http")
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?:"terjadi Kesalahan",null))
            Log.e("TAG","Login Error" + e.message)
        }
    }
    fun getMePeminjam(token:String) = flow {
        emit(Resource.loading(null))
        try {
            remote.getMePeminjam(token).let {
                if (it.isSuccessful){
                    val body=it.body()
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi Kesalahan",null))
                    Log.e("ERROR","Error Http")
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?:"terjadi Kesalahan",null))
            Log.e("TAG","Login Error" + e.message)
        }
    }
    fun getDataPeminjam(token:String,idPeminjam:String) = flow {
        emit(Resource.loading(null))
        try {
            remote.getDataPeminjam(token,idPeminjam).let {
                if (it.isSuccessful){
                    val body=it.body()
                    emit(Resource.success(body?.data))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi Kesalahan",null))
                    Log.e("ERROR","Error Http")
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?:"terjadi Kesalahan",null))
            Log.e("TAG","Login Error" + e.message)
        }
    }
}