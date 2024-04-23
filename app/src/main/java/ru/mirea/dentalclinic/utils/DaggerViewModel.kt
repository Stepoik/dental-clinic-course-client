package ru.mirea.dentalclinic.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.mirea.dentalclinic.App
import ru.mirea.dentalclinic.di.AppComponent

@Composable
inline fun <reified VM : ViewModel> daggerViewModel(crossinline body: () -> VM): VM {
    return viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return body.invoke() as T
        }
    }
    )
}

@Composable
fun appComponent(): AppComponent {
    val application: App = LocalContext.current.applicationContext as App
    return application.appComponent
}