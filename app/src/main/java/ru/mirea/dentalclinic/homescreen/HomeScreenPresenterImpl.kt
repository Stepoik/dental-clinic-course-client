package ru.mirea.dentalclinic.homescreen

import kotlinx.coroutines.flow.StateFlow

class HomeScreenPresenterImpl(
    private val viewModel: HomeViewModel
): HomeScreenPresenter {
    override val state: StateFlow<HomeScreenState> = viewModel.state

    override fun update() {
        viewModel.update()
    }
}