package com.santhosh.gramasanjeevini.model

data class Medicine(

    val id: String = "",
    val name: String = "",
    val stock: Int = 0,
    val pharmacy: String = "",
    val lifeSaving: Boolean = false,
    val expiryDate: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)