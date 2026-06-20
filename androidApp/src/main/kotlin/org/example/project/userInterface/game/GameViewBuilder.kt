package org.example.project.userInterface.game

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import org.example.project.Factory
import org.example.project.SharedLogicFactory

@Composable
fun GameViewBuilder() {
    val dataSource = Factory.createWeaponDataSource()
    val vmFactory = viewModelFactory {
        initializer {
            GameViewModel(presenterFactory = { scope ->
                SharedLogicFactory.createGamePresenter(
                    dataSource,
                    scope
                )
            })
        }
    }
    val viewModel: GameViewModel = viewModel(factory = vmFactory)
    GameView(viewModel)
}