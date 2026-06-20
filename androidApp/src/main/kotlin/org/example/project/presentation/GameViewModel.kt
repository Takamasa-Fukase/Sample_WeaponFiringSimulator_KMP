package org.example.project.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.domain.entities.Weapon

data class GameUiState(
    val loadedWeapons: List<Weapon> = emptyList(),
    var isLoading: Boolean = false,
    var currentWeapon: Weapon? = null
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