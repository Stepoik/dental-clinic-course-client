package ru.mirea.dentalclinic.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.mirea.dentalclinic.di.features.DoctorListComponent
import ru.mirea.dentalclinic.di.features.DoctorListDependencies
import ru.mirea.dentalclinic.di.features.DoctorPageComponent
import ru.mirea.dentalclinic.di.features.DoctorPageDependencies
import ru.mirea.dentalclinic.di.features.HomeScreenComponent
import ru.mirea.dentalclinic.di.features.HomeScreenDependencies
import ru.mirea.dentalclinic.di.modules.ComponentsModule
import ru.mirea.dentalclinic.di.modules.DoctorModule
import ru.mirea.dentalclinic.di.modules.ProcedureModule
import ru.mirea.dentalclinic.di.modules.RetrofitModule
import javax.inject.Scope

@Scope
annotation class AppScope

@AppScope
@Component(modules = [ComponentsModule::class, RetrofitModule::class, DoctorModule::class, ProcedureModule::class])
interface AppComponent : HomeScreenDependencies, DoctorListDependencies, DoctorPageDependencies {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun homeScreenComponent(): HomeScreenComponent

    fun doctorListComponent(): DoctorListComponent

    fun doctorPageComponent(): DoctorPageComponent
}