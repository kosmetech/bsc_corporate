package com.kosme.bsc_corporate.api

import com.kosme.bsc_corporate.data.ResponseItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("list_skor_filter.php?")
    fun getSkor(@Query("tanggal_isi") date: String?): Call<ResponseItem?>?

    companion object {

        val BASE_URL = "https://kosmeproduct.com/bsc/list_skor_filter.php?"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}