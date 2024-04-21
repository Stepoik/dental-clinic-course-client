package ru.mirea.dentalclinic.di.modules

import dagger.Module
import dagger.Provides
import ru.mirea.dentalclinic.di.AppComponent
import ru.mirea.dentalclinic.di.features.homescreen.DaggerHomeScreenComponent
import ru.mirea.dentalclinic.di.features.homescreen.HomeScreenComponent

@Module
interface ComponentsModule {
    companion object {
        @Provides
        fun provideHomeScreenComponent(appComponent: AppComponent): HomeScreenComponent {
            return DaggerHomeScreenComponent.factory().create(appComponent)
        }
    }
}