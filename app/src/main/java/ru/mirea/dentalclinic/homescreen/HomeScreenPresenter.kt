package ru.mirea.dentalclinic.homescreen

import kotlinx.coroutines.flow.StateFlow

interface HomeScreenPresenter {
    val state: StateFlow<HomeScreenState>
    fun update()
}