package com.santhosh.gramasanjeevini.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.santhosh.gramasanjeevini.ui.screens.OwnerHomeScreen
import com.santhosh.gramasanjeevini.ui.theme.screens.HomeScreen
import com.santhosh.gramasanjeevini.ui.theme.screens.LoginScreen
import com.santhosh.gramasanjeevini.ui.theme.screens.AddMedicineScreen
import com.santhosh.gramasanjeevini.ui.theme.screens.EmergencyScreen
import com.santhosh.gramasanjeevini.ui.theme.screens.RoleSelectionScreen
import com.santhosh.gramasanjeevini.ui.theme.screens.OwnerLoginScreen
import com.santhosh.gramasanjeevini.ui.screens.OwnerHomeScreen
import com.santhosh.gramasanjeevini.ui.theme.screens.ManageMedicineScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "roleSelection"
    ) {


        composable("home") {
            HomeScreen(navController)
        }
        composable("addMedicine") {
            AddMedicineScreen(navController)
        }

        composable("emergency") {
            EmergencyScreen(navController)
        }

        composable("roleSelection") {
            RoleSelectionScreen(navController)
        }

        composable("userHome") {
            HomeScreen(navController)
        }

        composable("ownerLogin") {
            OwnerLoginScreen(navController)
        }

        composable("ownerHome") {
            OwnerHomeScreen(navController)
        }
        composable("manageMedicine") {
            ManageMedicineScreen(navController)
        }
    }
}