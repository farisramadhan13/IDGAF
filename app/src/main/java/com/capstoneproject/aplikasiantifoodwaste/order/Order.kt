package com.capstoneproject.aplikasiantifoodwaste.order

data class Order(
    val idOrder: String? = null,
    val gambarO: String? = null,
    val namaMakananO: String? = null,
    val jam : String? = null,
    val jumlah : String? = null,
    val status : String? =null,
    val idPenerima: String? = null,
    val idPemberi : String? = null
)
