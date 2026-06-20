package org.example.project.domain.useCases

import org.example.project.domain.entities.Weapon
import org.example.project.domain.repositoryInterfaces.WeaponRepositoryInterface

interface WeaponResourceGetUseCaseInterface {
    suspend fun execute(id: Int): Weapon
}

class WeaponResourceGetUseCase(
    private val weaponRepository: WeaponRepositoryInterface
): WeaponResourceGetUseCaseInterface {
    override suspend fun execute(id: Int): Weapon {
        return weaponRepository.getById(id = id)
    }
}