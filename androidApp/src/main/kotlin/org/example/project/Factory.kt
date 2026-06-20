package org.example.project

import org.example.project.data.dataSourceInterfaces.WeaponDataSourceInterface
import org.example.project.infrastructure.storages.weaponConstantData.WeaponDataSource

object Factory {
    fun createWeaponDataSource(): WeaponDataSourceInterface {
        return WeaponDataSource()
    }
}