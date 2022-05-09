package com.example.myapplication.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("data.json")
    fun getData(): Call<TotalResponse>


}