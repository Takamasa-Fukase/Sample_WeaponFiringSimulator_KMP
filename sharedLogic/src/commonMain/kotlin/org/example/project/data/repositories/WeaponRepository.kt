package org.example.project.data.repositories
import kotlinx.coroutines.delay
import org.example.project.data.dataSourceInterfaces.WeaponDataSourceInterface
import org.example.project.domain.entities.Weapon
import org.example.project.domain.repositoryInterfaces.WeaponRepositoryInterface

class WeaponRepository(
    private val weaponDataSource: WeaponDataSourceInterface
): WeaponRepositoryInterface {
    override suspend fun getById(id: Int): Weapon {
        delay(1000)
        val weapon = weaponDataSource.list.firstOrNull { it.id == id }
        return weapon ?: error("WeaponDataSourceにid: ${id}の武器が存在しません")
    }
}