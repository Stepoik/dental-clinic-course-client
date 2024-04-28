package ru.mirea.dentalclinic.di.modules

import dagger.Module
import dagger.Provides
import ru.mirea.dentalclinic.di.AppComponent
import ru.mirea.dentalclinic.di.features.AppointmentScreenComponent
import ru.mirea.dentalclinic.di.features.DaggerAppointmentScreenComponent
import ru.mirea.dentalclinic.di.features.DaggerDoctorListComponent
import ru.mirea.dentalclinic.di.features.DaggerDoctorPageComponent
import ru.mirea.dentalclinic.di.features.DaggerHomeScreenComponent
import ru.mirea.dentalclinic.di.features.DaggerLoginScreenComponent
import ru.mirea.dentalclinic.di.features.DaggerMainActivityComponent
import ru.mirea.dentalclinic.di.features.DaggerRegistrationScreenComponent
import ru.mirea.dentalclinic.di.features.DaggerSplashScreenComponent
import ru.mirea.dentalclinic.di.features.DoctorListComponent
import ru.mirea.dentalclinic.di.features.DoctorPageComponent
import ru.mirea.dentalclinic.di.features.HomeScreenComponent
import ru.mirea.dentalclinic.di.features.LoginScreenComponent
import ru.mirea.dentalclinic.di.features.MainActivityComponent
import ru.mirea.dentalclinic.di.features.RegistrationScreenComponent
import ru.mirea.dentalclinic.di.features.SplashScreenComponent

@Module
interface ComponentsModule {
    companion object {
        @Provides
        fun provideHomeScreenComponent(appComponent: AppComponent): HomeScreenComponent {
            return DaggerHomeScreenComponent.factory().create(appComponent)
        }

        @Provides
        fun provideDoctorListComponent(appComponent: AppComponent): DoctorListComponent {
            return DaggerDoctorListComponent.factory().create(appComponent)
        }

        @Provides
        fun provideDoctorPageComponent(appComponent: AppComponent): DoctorPageComponent {
            return DaggerDoctorPageComponent.factory().create(appComponent)
        }

        @Provides
        fun provideAppointmentScreenComponent(appComponent: AppComponent): AppointmentScreenComponent {
            return DaggerAppointmentScreenComponent.factory().create(appComponent)
        }

        @Provides
        fun provideMainActivityComponent(appComponent: AppComponent): MainActivityComponent {
            return DaggerMainActivityComponent.factory().create(appComponent)
        }

        @Provides
        fun provideSplashScreenComponent(appComponent: AppComponent): SplashScreenComponent {
            return DaggerSplashScreenComponent.factory().create(appComponent)
        }

        @Provides
        fun provideLoginScreenComponent(appComponent: AppComponent): LoginScreenComponent {
            return DaggerLoginScreenComponent.factory().create(appComponent)
        }

        @Provides
        fun provideRegistrationScreenComponent(appComponent: AppComponent): RegistrationScreenComponent {
            return DaggerRegistrationScreenComponent.factory().create(appComponent)
        }
    }
}