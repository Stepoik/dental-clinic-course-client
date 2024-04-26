package ru.mirea.dentalclinic.di.modules

import dagger.Module
import dagger.Provides
import ru.mirea.dentalclinic.di.AppComponent
import ru.mirea.dentalclinic.di.features.AppointmentScreenComponent
import ru.mirea.dentalclinic.di.features.DaggerAppointmentScreenComponent
import ru.mirea.dentalclinic.di.features.DaggerDoctorListComponent
import ru.mirea.dentalclinic.di.features.DaggerDoctorPageComponent
import ru.mirea.dentalclinic.di.features.DaggerHomeScreenComponent
import ru.mirea.dentalclinic.di.features.DoctorListComponent
import ru.mirea.dentalclinic.di.features.DoctorPageComponent
import ru.mirea.dentalclinic.di.features.HomeScreenComponent

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
    }
}