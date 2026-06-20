//
//  Factory.swift
//  iosApp
//
//  Created by ウルトラ深瀬 on 2026/06/20.
//

import Foundation
import SharedLogic

struct Factory {
    static func create() -> WeaponDataSourceInterface {
        return WeaponDataSource()
    }
    
    static func create() -> WeaponRepositoryInterface {
        return WeaponRepository(weaponDataSource: create())
    }
    
    static func create() -> WeaponResourceGetUseCaseInterface {
        return WeaponResourceGetUseCase(weaponRepository: create())
    }
    
    static func create() -> GamePresenter {
        return GamePresenter(weaponResourceGetUseCase: create())
    }
}
