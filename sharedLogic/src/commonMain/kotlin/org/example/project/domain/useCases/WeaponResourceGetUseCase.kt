package org.example.project.domain.useCases

import org.example.project.data.repositories.WeaponRepository
import org.example.project.domain.entities.Weapon

interface WeaponResourceGetUseCaseInterface {
    suspend fun execute(id: Int): Weapon
}

class WeaponResourceGetUseCase(
    private val weaponRepository: WeaponRepository
): WeaponResourceGetUseCaseInterface {
    override suspend fun execute(id: Int): Weapon {
        return weaponRepository.getById(id = id)
    }
}