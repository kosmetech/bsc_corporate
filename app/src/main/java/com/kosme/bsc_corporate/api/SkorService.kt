package com.kosme.bsc_corporate.api

import com.kosme.bsc_corporate.data.CekDataResponse
import com.kosme.bsc_corporate.data.DataGrafik
import com.kosme.bsc_corporate.data.ResponseItem
import com.kosme.bsc_corporate.data.Skor
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SkorService {
    @GET("list_skor_filter.php?")
    fun getSkor(@Query("tanggal_isi") date: String?): Call<List<ResponseItem>>


    @GET("cek_data.php?")
    fun getData(@Query("tanggal") date: String?): Call<List<CekDataResponse>>

    @GET("grafik_filter.php?")
    fun getGrafik(
        @Query("id_kategori") category : String,
        @Query("tanggal_start") startDate : String,
        @Query("tanggal_end") endDate : String
    ) : Call<List<DataGrafik>>

}