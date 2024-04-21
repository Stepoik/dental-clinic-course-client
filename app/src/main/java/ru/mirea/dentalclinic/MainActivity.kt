package ru.mirea.dentalclinic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.mirea.dentalclinic.homescreen.view.HomeScreen
import ru.mirea.dentalclinic.homescreen.HomeScreenPresenterImpl
import ru.mirea.dentalclinic.homescreen.HomeViewModel
import ru.mirea.dentalclinic.homescreen.view.HOME_SCREEN_ROUTE
import ru.mirea.dentalclinic.homescreen.view.homeScreen
import ru.mirea.dentalclinic.ui.theme.DentalClinicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DentalClinicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = HOME_SCREEN_ROUTE) {
                        homeScreen()
                    }
                }
            }
        }
    }
}