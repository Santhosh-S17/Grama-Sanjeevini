package com.santhosh.gramasanjeevini.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.santhosh.gramasanjeevini.model.Medicine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyScreen(navController: NavHostController) {

    var emergencyMedicines by remember {
        mutableStateOf<List<Medicine>>(emptyList())
    }

    LaunchedEffect(Unit) {

        FirebaseFirestore.getInstance()
            .collection("medicines")
            .whereEqualTo("lifeSaving", true)
            .addSnapshotListener { result, _ ->

                val list = mutableListOf<Medicine>()

                result?.documents?.forEach {

                    val medicine = it.toObject(Medicine::class.java)

                    if (medicine != null) {
                        list.add(medicine)
                    }
                }

                emergencyMedicines = list
            }
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Emergency Medicines")
                },

                navigationIcon = {

                    TextButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Text("← Back")
                    }
                }
            )
        }

    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            items(emergencyMedicines) { medicine ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = medicine.name,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(text = "Stock: ${medicine.stock}")

                        Text(text = "Pharmacy: ${medicine.pharmacy}")

                        Surface(
                            color = Color.Red
                        ) {

                            Text(
                                text = " EMERGENCY ",
                                color = Color.White,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}