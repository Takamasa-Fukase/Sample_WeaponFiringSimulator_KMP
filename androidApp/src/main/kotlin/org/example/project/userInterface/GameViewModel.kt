package org.example.project.userInterface

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.example.project.domain.entities.Weapon
import org.example.project.presentation.game.GamePresenter

data class GameUiState(
    val loadedWeapons: List<Weapon> = emptyList(),
    val isLoading: Boolean = false,
    val currentWeapon: Weapon? = null
)

class GameViewModel(
    private val presenter: GamePresenter
) : ViewModel() {

    val uiState: StateFlow<GameUiState> = combine(
        presenter.loadedWeaponsFlow,
        presenter.isLoadingFlow,
        presenter.currentWeaponFlow,
    ) { weapons, isLoading, currentWeapon ->
        GameUiState(
            loadedWeapons = weapons,
            isLoading = isLoading,
            currentWeapon = currentWeapon,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GameUiState()
    )

    fun weaponSelected(id: Int) {
        presenter.weaponSelected(id)
    }

    fun resetButtonTapped() {
        presenter.resetButtonTapped()
    }
}