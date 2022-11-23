package com.example.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("api/users?page=2")
    fun getData(): Call<List<Data>>
}