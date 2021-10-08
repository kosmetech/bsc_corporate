package com.kosme.bsc_corporate.data

data class Skor(
	val response: List<ResponseItem?>? = null
)

data class ResponseItem(
	val jumlah_perkategori: Float? = null,
	val id_kategori: String? = null,
	val koneksi: String? = null,
	val bagi_kategori: Int? = null,
	val kategori: String? = null
)

