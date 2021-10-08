package com.kosme.bsc_corporate.data

data class Grafik(
    val response: List<DataGrafik?>? = null
)

data class DataGrafik(
    val jumlah_perkategori: Float? = null,
    val id_kategori: String? = null,
    val koneksi: String? = null,
    val bagi_kategori: Int? = null,
    val kategori: String? = null,
    val tanggal_isi: String? = null,
    val persen : Float? = null
)

