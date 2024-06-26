package ru.mirea.dentalclinic

import android.app.Application
import ru.mirea.dentalclinic.di.AppComponent
import ru.mirea.dentalclinic.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.factory().create(this)
}