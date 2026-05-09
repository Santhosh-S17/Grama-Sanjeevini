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
fun ManageMedicineScreen(
    navController: NavHostController
) {

    var medicineList by remember {
        mutableStateOf<List<Medicine>>(emptyList())
    }

    LaunchedEffect(Unit) {

        FirebaseFirestore.getInstance()
            .collection("medicines")
            .addSnapshotListener { result, _ ->

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

        topBar = {

            TopAppBar(

                title = {
                    Text("Manage Medicines")
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

            items(medicineList) { medicine ->

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

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {

                                FirebaseFirestore.getInstance()
                                    .collection("medicines")
                                    .document(medicine.id)
                                    .update(
                                        "stock",
                                        medicine.stock + 10
                                    )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text("Update Stock +10")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {

                                FirebaseFirestore.getInstance()
                                    .collection("medicines")
                                    .document(medicine.id)
                                    .delete()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text("Delete Medicine")
                        }
                    }
                }
            }
        }
    }
}
