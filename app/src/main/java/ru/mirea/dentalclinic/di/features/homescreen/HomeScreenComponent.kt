package ru.mirea.dentalclinic.di.features.homescreen

import android.content.Context
import dagger.Component
import ru.mirea.dentalclinic.domain.usecases.GetBestDoctorsUseCase
import ru.mirea.dentalclinic.domain.usecases.GetProceduresUseCase
import ru.mirea.dentalclinic.homescreen.HomeViewModel
import javax.inject.Scope

@Scope
annotation class HomeScreenScope

interface HomeScreenDependencies {
    val getBestDoctorsUseCase: GetBestDoctorsUseCase
    val getProceduresUseCase: GetProceduresUseCase
    val context: Context
}

@HomeScreenScope
@Component(dependencies = [HomeScreenDependencies::class])
interface HomeScreenComponent {
    @Component.Factory
    interface Factory {
        fun create(dependencies: HomeScreenDependencies): HomeScreenComponent
    }

    fun homeViewModel(): HomeViewModel
}