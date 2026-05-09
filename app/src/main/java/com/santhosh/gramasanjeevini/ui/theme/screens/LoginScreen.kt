package com.santhosh.gramasanjeevini.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.santhosh.gramasanjeevini.ui.theme.BackgroundColor
import com.santhosh.gramasanjeevini.ui.theme.PrimaryGreen
import com.santhosh.gramasanjeevini.ui.theme.TextSecondary
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.santhosh.gramasanjeevini.ui.theme.TextPrimary
import com.santhosh.gramasanjeevini.R

@Composable
fun LoginScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundColor
    )
    {
        Column(
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
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        )
        {




            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Email")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Password")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    isLoading = true
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            isLoading = false
                            if (it.isSuccessful) {
                                navController.navigate("home")
                            }
                        }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {

                if (isLoading) {

                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )

                } else {

                    Text("Login")
                }
            }
        }
    }
}