package ru.mirea.dentalclinic.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Component.Factory
import ru.mirea.dentalclinic.di.features.homescreen.HomeScreenComponent
import ru.mirea.dentalclinic.di.features.homescreen.HomeScreenDependencies
import ru.mirea.dentalclinic.di.modules.ComponentsModule
import ru.mirea.dentalclinic.di.modules.DoctorModule
import ru.mirea.dentalclinic.di.modules.ProcedureModule
import ru.mirea.dentalclinic.di.modules.RetrofitModule
import javax.inject.Scope

@Scope
annotation class AppScope

@AppScope
@Component(modules = [ComponentsModule::class, RetrofitModule::class, DoctorModule::class, ProcedureModule::class])
interface AppComponent : HomeScreenDependencies {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun homeScreenComponent(): HomeScreenComponent
}