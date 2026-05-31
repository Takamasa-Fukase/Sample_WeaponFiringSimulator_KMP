package org.example.project.data.dataSourceInterfaces

import org.example.project.domain.entities.Weapon

interface WeaponDataSourceInterface {
    val list: List<Weapon>
}