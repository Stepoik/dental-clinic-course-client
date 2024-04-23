package ru.mirea.dentalclinic.presentation.doctorlist.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mirea.dentalclinic.di.features.DoctorListComponent
import ru.mirea.dentalclinic.presentation.doctorlist.DoctorListPresenterImpl
import ru.mirea.dentalclinic.utils.ComponentViewModel
import ru.mirea.dentalclinic.utils.appComponent
import ru.mirea.dentalclinic.utils.daggerViewModel

const val DOCTOR_LIST_ROUTE = "doctor_list"

fun NavGraphBuilder.doctorList() {
    composable(DOCTOR_LIST_ROUTE) {
        val appScope = appComponent()
        val componentViewModel: ComponentViewModel<DoctorListComponent> = daggerViewModel {
            ComponentViewModel(appScope.doctorListComponent())
        }
        val viewModel = daggerViewModel {
            componentViewModel.component.doctorListViewModel()
        }
        val presenter = DoctorListPresenterImpl(viewModel = viewModel)
        DoctorListScreen(doctorListPresenter = presenter)
    }
}

fun NavController.navigateToDoctorList() {
    navigate(DOCTOR_LIST_ROUTE)
}