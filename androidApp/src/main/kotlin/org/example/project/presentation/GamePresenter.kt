package org.example.project.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.domain.entities.Weapon
import org.example.project.domain.useCases.WeaponResourceGetUseCaseInterface

class GamePresenter(
    private val weaponResourceGetUseCase: WeaponResourceGetUseCaseInterface,
    private val coroutineScope: CoroutineScope,
) {
    private val _selectedWeaponIdFlow = MutableStateFlow<Int?>(null)
    private val _loadedWeaponsFlow = MutableStateFlow<List<Weapon>>(listOf())
    private val _isLoadingFlow = MutableStateFlow<Boolean>(false)

    val loadedWeaponsFlow = _loadedWeaponsFlow.asStateFlow()
    val isLoadingFlow = _isLoadingFlow.asStateFlow()
    val currentWeaponFlow: StateFlow<Weapon?> = _selectedWeaponIdFlow
        .combine(loadedWeaponsFlow) { id, weapons ->
            weapons.firstOrNull { it.id == (id ?: 0) }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun weaponSelected(id: Int) {
        _selectedWeaponIdFlow.value = id

        if (_loadedWeaponsFlow.value.any { it.id == id }) {
            return
        }

        coroutineScope.launch {
            _isLoadingFlow.value = true
            try {
                val weapon = weaponResourceGetUseCase.execute(id)
                val appendedList = _loadedWeaponsFlow.value + listOf(weapon)
                _loadedWeaponsFlow.value = appendedList
            } catch (e: Exception) {
                println("GameViewModel エラーが発生しました: $e")
            } finally {
                _isLoadingFlow.value = false
            }
        }
    }

    fun resetButtonTapped() {
        _loadedWeaponsFlow.value = listOf()
    }
}