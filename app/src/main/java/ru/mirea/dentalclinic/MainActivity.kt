package ru.mirea.dentalclinic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.mirea.dentalclinic.di.features.MainActivityComponent
import ru.mirea.dentalclinic.presentation.appointment.view.appointmentScreen
import ru.mirea.dentalclinic.presentation.authentication.authentication
import ru.mirea.dentalclinic.presentation.doctorsearch.view.doctorList
import ru.mirea.dentalclinic.presentation.doctorpage.view.doctorPage
import ru.mirea.dentalclinic.presentation.homescreen.view.homeScreen
import ru.mirea.dentalclinic.presentation.proceduredoctors.procedureDoctors
import ru.mirea.dentalclinic.presentation.procedures.procedures
import ru.mirea.dentalclinic.presentation.splash.view.SPLASH_SCREEN_ROUTE
import ru.mirea.dentalclinic.presentation.splash.view.splash
import ru.mirea.dentalclinic.ui.theme.DentalClinicTheme

class MainActivity : ComponentActivity() {
    lateinit var component: MainActivityComponent
    private val viewModel by viewModels<MainViewModel> {
        MainViewModel.Factory {
            component.mainViewModel()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val applicationComponent = (applicationContext as App).appComponent
        component = applicationComponent.mainActivityComponent()
        setContent {
            val screenState = viewModel.state.collectAsState().value
            DentalClinicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = SPLASH_SCREEN_ROUTE) {
                        homeScreen(navController)
                        doctorList(navController)
                        doctorPage(navController)
                        appointmentScreen(navController)
                        authentication(navController)
                        splash(navController)
                        procedureDoctors(navController)
                        procedures(navController)
                    }
                }
            }
        }
    }
}