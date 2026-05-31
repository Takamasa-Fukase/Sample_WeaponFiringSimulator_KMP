//
//  WeaponDataSource.swift
//  iosApp
//
//  Created by ウルトラ深瀬 on 2026/05/31.
//

import Foundation
import SharedLogic

final class WeaponDataSource: WeaponDataSourceInterface {
    let list: [Weapon] = [
        .init(id: 0, name: "M1911", capacity: 7, imageName: "pistol"),
        .init(id: 1, name: "RPG-7", capacity: 1, imageName: "rocket-launcher")
    ]
}
