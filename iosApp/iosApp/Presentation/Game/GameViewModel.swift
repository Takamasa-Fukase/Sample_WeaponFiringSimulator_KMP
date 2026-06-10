//
//  GameViewModel.swift
//  iosApp
//
//  Created by ウルトラ深瀬 on 2026/05/31.
//

import Foundation
import Observation
import SharedLogic

@Observable
class GameViewModel {
    private var presenter: GamePresenter?
    private(set) var loadedWeapons: [Weapon] = []
    private(set) var isLoading: Bool = false
    var currentWeapon: Weapon? {
        return presenter?.currentWeapon
    }
    
    func inject(presenter: GamePresenter) {
        self.presenter = presenter
    }
    
    func weaponSelected(id: Int) {
        presenter?.weaponSelected(id: id)
    }
    
    func resetButtonTapped() {
        presenter?.resetButtonTapped()
    }
    
    // Presenterから呼ばれるやつ
    func setLoadedWeapons(_ value: [Weapon]) {
        loadedWeapons = value
    }
    
    func setIsLoading(_ value: Bool) {
        isLoading = value
    }
    
//    func setCurrentWeapon(_ value: Weapon?) {
//        currentWeapon = value
//    }
}
