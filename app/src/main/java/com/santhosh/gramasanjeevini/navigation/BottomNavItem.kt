package com.santhosh.gramasanjeevini.navigation

sealed class BottomNavItem(
    val route: String,
    val title: String
) {

    object Home : BottomNavItem(
        "home",
        "Home"
    )

    object AddMedicine : BottomNavItem(
        "addMedicine",
        "Add"
    )

    object Emergency : BottomNavItem(
        "emergency",
        "Emergency"
    )
}