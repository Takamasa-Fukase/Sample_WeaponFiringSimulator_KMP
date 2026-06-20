package org.example.project

import kotlinx.coroutines.CoroutineScope
import org.example.project.data.dataSourceInterfaces.WeaponDataSourceInterface
import org.example.project.data.repositories.WeaponRepository
import org.example.project.domain.repositoryInterfaces.WeaponRepositoryInterface
import org.example.project.domain.useCases.WeaponResourceGetUseCase
import org.example.project.domain.useCases.WeaponResourceGetUseCaseInterface
import org.example.project.presentation.game.GamePresenter

object SharedLogicFactory {
    fun createWeaponRepository(
        weaponDataSource: WeaponDataSourceInterface
    ): WeaponRepositoryInterface {
        return WeaponRepository(weaponDataSource)
    }

    fun createWeaponResourceGetUseCase(
        weaponDataSource: WeaponDataSourceInterface
    ): WeaponResourceGetUseCaseInterface {
        return WeaponResourceGetUseCase(createWeaponRepository(weaponDataSource))
    }

    fun createGamePresenter(
        weaponDataSource: WeaponDataSourceInterface,
        coroutineScope: CoroutineScope,
    ): GamePresenter {
        return GamePresenter(
            createWeaponResourceGetUseCase(weaponDataSource),
            coroutineScope
        )
    }
}