package ru.mirea.dentalclinic.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.mirea.dentalclinic.di.features.AppointmentDependencies
import ru.mirea.dentalclinic.di.features.AppointmentScreenComponent
import ru.mirea.dentalclinic.di.features.DoctorListComponent
import ru.mirea.dentalclinic.di.features.DoctorListDependencies
import ru.mirea.dentalclinic.di.features.DoctorPageComponent
import ru.mirea.dentalclinic.di.features.DoctorPageDependencies
import ru.mirea.dentalclinic.di.features.HomeScreenComponent
import ru.mirea.dentalclinic.di.features.HomeScreenDependencies
import ru.mirea.dentalclinic.di.features.LoginScreenComponent
import ru.mirea.dentalclinic.di.features.LoginScreenDependencies
import ru.mirea.dentalclinic.di.features.MainActivityComponent
import ru.mirea.dentalclinic.di.features.MainActivityDependencies
import ru.mirea.dentalclinic.di.features.RegistrationDependencies
import ru.mirea.dentalclinic.di.features.RegistrationScreenComponent
import ru.mirea.dentalclinic.di.features.SplashScreenComponent
import ru.mirea.dentalclinic.di.features.SplashScreenDependencies
import ru.mirea.dentalclinic.di.modules.AppointmentModule
import ru.mirea.dentalclinic.di.modules.AuthModule
import ru.mirea.dentalclinic.di.modules.ComponentsModule
import ru.mirea.dentalclinic.di.modules.DoctorModule
import ru.mirea.dentalclinic.di.modules.PatientModule
import ru.mirea.dentalclinic.di.modules.ProcedureModule
import ru.mirea.dentalclinic.di.modules.RetrofitModule
import javax.inject.Scope

@Scope
annotation class AppScope

@AppScope
@Component(
    modules = [
        ComponentsModule::class,
        RetrofitModule::class,
        DoctorModule::class,
        ProcedureModule::class,
        AppointmentModule::class,
        AuthModule::class,
        PatientModule::class
    ]
)
interface AppComponent :
    HomeScreenDependencies,
    DoctorListDependencies,
    DoctorPageDependencies,
    AppointmentDependencies,
    MainActivityDependencies,
    SplashScreenDependencies,
    LoginScreenDependencies,
    RegistrationDependencies {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun homeScreenComponent(): HomeScreenComponent

    fun doctorListComponent(): DoctorListComponent

    fun doctorPageComponent(): DoctorPageComponent

    fun appointmentComponent(): AppointmentScreenComponent

    fun mainActivityComponent(): MainActivityComponent

    fun splashScreenComponent(): SplashScreenComponent

    fun loginScreenComponent(): LoginScreenComponent

    fun registrationScreenComponent(): RegistrationScreenComponent
}