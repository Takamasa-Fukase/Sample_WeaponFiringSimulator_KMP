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
    private(set) var loadedWeapons: [Weapon] = []
    private(set) var isLoading: Bool = false
    private(set) var currentWeapon: Weapon?
    
    private let presenter: GamePresenter
    private let coroutineCancelHandler: (() -> Void)?
    private var tasks: [Task<Void, Never>] = []
    
    init(
        presenter: GamePresenter,
        coroutineCancelHandler: (() -> Void)?
    ) {
        self.presenter = presenter
        self.coroutineCancelHandler = coroutineCancelHandler
        
        tasks.append(Task { [weak self] in
            for await weapons in presenter.loadedWeaponsFlow {
                self?.loadedWeapons = weapons
            }
        })
        
        tasks.append(Task { [weak self] in
            for await isLoading in presenter.isLoadingFlow {
                self?.isLoading = isLoading.boolValue
            }
        })
        
        tasks.append(Task { [weak self] in
            for await currentWeapon in presenter.currentWeaponFlow {
                self?.currentWeapon = currentWeapon
            }
        })
    }
    
    deinit {
        coroutineCancelHandler?()
        tasks.forEach { task in
            task.cancel()
        }
    }
    
    func weaponSelected(id: Int) {
        presenter.weaponSelected(id: Int32(id))
    }
    
    func resetButtonTapped() {
        presenter.resetButtonTapped()
    }
}
