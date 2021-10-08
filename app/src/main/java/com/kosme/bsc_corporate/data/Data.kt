package com.kosme.bsc_corporate.data

data class Data(
    val response: List<CekDataResponse?>? = null
)

data class CekDataResponse(
    val id_kategori: String? = null,
    val kategori: String? = null,
    val kpi: String? = null,
    val file: String? = null,
    val target: String? = null,
    val score: Float? = null,
    val score_isi: Float? = null,
    val tanggal_isi: String? = null,
    val koneksi: String? = null
)