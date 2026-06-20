//
//  GameViewModel.swift
//  iosApp
//
//  Created by ウルトラ深瀬 on 2026/05/31.
//

import Foundation
import Observation
import Combine
import SharedLogic

@Observable
class GameViewModel {
    private let presenter: GamePresenter
    private var cancellables: Set<AnyCancellable> = []

    private(set) var loadedWeapons: [Weapon] = []
    private(set) var isLoading: Bool = false
    private(set) var currentWeapon: Weapon?
    
    init(presenter: GamePresenter) {
        self.presenter = presenter
        
        presenter.loadedWeaponsPublisher
            .sink { [weak self] weapons in
                self?.loadedWeapons = weapons
            }
            .store(in: &cancellables)
        
        presenter.isLoadingPublisher
            .sink { [weak self] isLoading in
                self?.isLoading = isLoading
            }
            .store(in: &cancellables)
        
        presenter.currentWeaponPublisher
            .sink { [weak self] weapon in
                self?.currentWeapon = weapon
            }
            .store(in: &cancellables)
    }
    
    func weaponSelected(id: Int) {
        presenter.weaponSelected(id: id)
    }
    
    func resetButtonTapped() {
        presenter.resetButtonTapped()
    }
}
