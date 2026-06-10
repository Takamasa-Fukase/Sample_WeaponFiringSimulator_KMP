package org.example.project.infrastructure.storages.weaponConstantData

import org.example.project.data.dataSourceInterfaces.WeaponDataSourceInterface
import org.example.project.domain.entities.Weapon

class WeaponDataSource: WeaponDataSourceInterface {
    override val list: List<Weapon> = listOf(
        Weapon(
            id = 0,
            name = "M1911",
            capacity = 7,
            imageName = "pistol",
        ),
        Weapon(
            id = 1,
            name = "RPG-7",
            capacity = 1,
            imageName = "rocket_launcher",
        ),
    )
}