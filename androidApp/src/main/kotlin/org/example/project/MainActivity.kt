package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.data.repositories.WeaponRepository
import org.example.project.domain.useCases.WeaponResourceGetUseCase
import org.example.project.infrastructure.storages.weaponConstantData.WeaponDataSource
import org.example.project.presentation.GamePresenter
import org.example.project.presentation.GameView
import org.example.project.presentation.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            GameView(viewModel = buildGameViewModel())
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    GameView(viewModel = buildGameViewModel())
}

private fun buildGameViewModel(): GameViewModel {
    val dataSource = WeaponDataSource()
    val repository = WeaponRepository(weaponDataSource = dataSource)
    val useCase = WeaponResourceGetUseCase(weaponRepository = repository)
    val presenter = GamePresenter(weaponResourceGetUseCase = useCase)
    val viewModel = GameViewModel(presenter = presenter)
    return viewModel
}