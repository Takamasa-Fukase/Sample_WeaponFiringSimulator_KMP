//
//  Factory.swift
//  iosApp
//
//  Created by ウルトラ深瀬 on 2026/06/20.
//

import Foundation
import SharedLogic

struct Factory {
    private init() {}
    
    static func create() -> WeaponDataSourceInterface {
        return WeaponDataSource()
    }
}
