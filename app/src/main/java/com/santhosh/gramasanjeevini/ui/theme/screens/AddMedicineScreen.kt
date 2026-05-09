package com.santhosh.gramasanjeevini.ui.theme.screens

import android.R.attr.padding
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.santhosh.gramasanjeevini.model.Medicine
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
import com.santhosh.gramasanjeevini.ui.theme.PrimaryGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineScreen( navController: NavHostController) {

    var medicineName by remember {
        mutableStateOf("")
    }

    var stock by remember {
        mutableStateOf("")
    }

    var pharmacy by remember {
        mutableStateOf("")
    }

    var expiryDate by remember {
        mutableStateOf("")
    }

    var latitude by remember {
        mutableStateOf("")
    }

    var longitude by remember {
        mutableStateOf("")
    }

    var lifeSaving by remember {
        mutableStateOf(false)

    }
    var showMessage by remember {
        mutableStateOf(false)
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    var isLoading by remember {
        mutableStateOf(false)
    }


    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Add Medicine")
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
        },

        snackbarHost = {

            SnackbarHost(
                hostState = snackbarHostState
            ) { data ->

                Snackbar(
                    snackbarData = data,
                    containerColor = PrimaryGreen,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
            }
        },


    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {

            Text(
                text = "Add Medicine",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = medicineName,
                onValueChange = {
                    medicineName = it
                },
                label = {
                    Text("Medicine Name")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = stock,
                onValueChange = {
                    stock = it
                },
                label = {
                    Text("Stock Quantity")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = pharmacy,
                onValueChange = {
                    pharmacy = it
                },
                label = {
                    Text("Pharmacy Name")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = expiryDate,
                onValueChange = {
                    expiryDate = it
                },
                label = {
                    Text("Expiry Date (dd/MM/yyyy)")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = latitude,
                onValueChange = {
                    latitude = it
                },
                label = {
                    Text("Latitude")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = longitude,
                onValueChange = {
                    longitude = it
                },
                label = {
                    Text("Longitude")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row {

                Checkbox(
                    checked = lifeSaving,
                    onCheckedChange = {
                        lifeSaving = it
                    }
                )

                Text(
                    text = "Life Saving Drug"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    isLoading = true
                    val medicine = Medicine(
                        name = medicineName,
                        stock = stock.toIntOrNull() ?: 0,
                        pharmacy = pharmacy,
                        lifeSaving = lifeSaving,
                        expiryDate = "expiryDate",
                        latitude = latitude.toDoubleOrNull() ?: 0.0,
                        longitude = longitude.toDoubleOrNull() ?: 0.0
                    )

                    FirebaseFirestore.getInstance()
                        .collection("medicines")
                        .add(medicine)
                        .addOnSuccessListener {
                            isLoading = false
                            scope.launch {

                                snackbarHostState.showSnackbar(
                                    "Medicine Added Successfully"
                                )
                            }
                        }
                        .addOnFailureListener {

                            isLoading = false
                        }

                    medicineName = ""
                    stock = ""
                    pharmacy = ""
                    lifeSaving = false
                    expiryDate = ""
                    latitude = ""
                    longitude = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                if (isLoading) {

                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )

                } else {

                    Text("Save Medicine")
                }
            }
        }
    }
}