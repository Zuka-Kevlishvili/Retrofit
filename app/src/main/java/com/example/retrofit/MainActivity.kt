package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://reqres.in/"

class MainActivity : AppCompatActivity() {

    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerViewUsers: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewUsers = findViewById(R.id.recyclerView_users)
        recyclerViewUsers.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerViewUsers.layoutManager = linearLayoutManager

        getReqresData()
    }

    private fun getReqresData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<Data>?> {
            override fun onResponse(call: Call<List<Data>?>, response: Response<List<Data>?>) {
                val responseBody = response.body()!!

                recyclerAdapter = RecyclerAdapter(baseContext, responseBody)
                recyclerAdapter.notifyDataSetChanged()
                recyclerViewUsers.adapter = recyclerAdapter
            }

            override fun onFailure(call: Call<List<Data>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }
}