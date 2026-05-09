package com.santhosh.gramasanjeevini
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.santhosh.gramasanjeevini.navigation.AppNavigation
import com.santhosh.gramasanjeevini.ui.theme.screens.LoginScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppNavigation()
        }
    }
}