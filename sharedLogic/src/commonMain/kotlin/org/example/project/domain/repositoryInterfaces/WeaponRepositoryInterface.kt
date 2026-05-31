package org.example.project.domain.repositoryInterfaces

import org.example.project.domain.entities.Weapon

interface WeaponRepositoryInterface {
    suspend fun getById(id: Int): Weapon
}