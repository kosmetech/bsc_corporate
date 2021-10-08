package com.kosme.bsc_corporate.data

data class ResultUser(
	val resultUser: List<ResultUserItem?>? = null
)

data class Company(
	val bs: String? = null,
	val catchPhrase: String? = null,
	val name: String? = null
)

data class Geo(
	val lng: String? = null,
	val lat: String? = null
)

data class Address(
	val zipcode: String? = null,
	val geo: Geo? = null,
	val suite: String? = null,
	val city: String? = null,
	val street: String? = null
)

data class ResultUserItem(
	val website: String? = null,
	val address: Address? = null,
	val phone: String? = null,
	val name: String? = null,
	val company: Company? = null,
	val id: Int? = null,
	val email: String? = null,
	val username: String? = null
)

