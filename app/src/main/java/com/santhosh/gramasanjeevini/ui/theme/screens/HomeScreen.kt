package com.santhosh.gramasanjeevini.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.santhosh.gramasanjeevini.model.Medicine
import androidx.compose.material3.Scaffold
import androidx.compose.material3.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import android.location.Location
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.text.font.FontWeight
import com.santhosh.gramasanjeevini.navigation.BottomNavItem
import com.santhosh.gramasanjeevini.ui.theme.CardColor
import com.santhosh.gramasanjeevini.ui.theme.EmergencyRed
import com.santhosh.gramasanjeevini.ui.theme.TextPrimary
import com.santhosh.gramasanjeevini.ui.theme.TextSecondary
import com.santhosh.gramasanjeevini.ui.theme.EmergencyRed
import com.santhosh.gramasanjeevini.ui.theme.WarningYellow

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Brush

fun isNearExpiry(expiryDate: String): Boolean {

    return try {

        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val expiry = format.parse(expiryDate)

        val currentDate = Date()

        val difference = expiry.time - currentDate.time

        val days = difference / (1000 * 60 * 60 * 24)

        days <= 30

    } catch (e: Exception) {

        false
    }
}

fun calculateDistance(
    userLat: Double,
    userLng: Double,
    pharmacyLat: Double,
    pharmacyLng: Double
): Float {

    val results = FloatArray(1)

    Location.distanceBetween(
        userLat,
        userLng,
        pharmacyLat,
        pharmacyLng,
        results
    )

    return results[0] / 1000
}
@Composable
fun HomeScreen(navController: NavHostController) {

    var medicineList by remember {
        mutableStateOf<List<Medicine>>(emptyList())
    }

    var searchText by remember {
        mutableStateOf("")
    }

    val filteredList = medicineList.filter {

        it.name.contains(searchText, ignoreCase = true)
    }

    val userLatitude = 12.9716
    val userLongitude = 77.5946

    LaunchedEffect(Unit) {

        FirebaseFirestore.getInstance()
            .collection("medicines")
            .addSnapshotListener { result, error ->

                if (error != null) {
                    return@addSnapshotListener
                }

                val list = mutableListOf<Medicine>()

                result?.documents?.forEach { document ->

                    val medicine = document.toObject(Medicine::class.java)
                        ?.copy(id = document.id)

                    if (medicine != null) {
                        list.add(medicine)
                    }
                }

                medicineList = list
            }
    }



    Scaffold(

        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("home")
                    },
                    label = {
                        Text("Home")
                    },
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    }
                )


                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("emergency")
                    },
                    label = {
                        Text("Emergency")
                    },
                    icon = {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "Emergency"
                        )
                    }
                )
            }
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE8F5E9),
                            Color.White
                        )
                    )
                )
        ) {
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            item {

                Text(
                    text = "Grama-Sanjeevini",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            item {

                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(18.dp),
                    label = {
                        Text("Search Medicine")
                    },
                    leadingIcon = {
                        Text("🔍")
                    }
                )
            }

            items(filteredList) { medicine ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CardColor
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        val distance = calculateDistance(
                            userLatitude,
                            userLongitude,
                            medicine.latitude,
                            medicine.longitude
                        )
                        Text(
                            text = medicine.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )

                        Text(
                            text = "Stock: ${medicine.stock}",
                            color = TextSecondary
                        )

                        Text(
                            text = "Pharmacy: ${medicine.pharmacy}",
                            color = TextSecondary
                        )
                        Text(
                            text = String.format("Distance: %.2f km", distance)
                        )

                        Text(text = "Expiry: ${medicine.expiryDate}")

                        if (medicine.lifeSaving) {

                            Surface(
                                color = EmergencyRed,
                                shape = RoundedCornerShape(50)
                            ) {

                                Text(
                                    text = "EMERGENCY",
                                    color = Color.White,
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 6.dp
                                    )
                                )
                            }

                            if (isNearExpiry(medicine.expiryDate)) {

                                Spacer(modifier = Modifier.height(8.dp))

                                Surface(
                                    color = WarningYellow,
                                    shape = RoundedCornerShape(50)
                                ) {

                                    Text(
                                        text = "Near Expiry",
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                    )
                                }
                            }


                        }
                    }
                }
            }
        }}
    }}