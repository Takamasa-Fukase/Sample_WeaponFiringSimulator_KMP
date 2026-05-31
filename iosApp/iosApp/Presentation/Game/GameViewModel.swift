//
//  GameViewModel.swift
//  iosApp
//
//  Created by ウルトラ深瀬 on 2026/05/31.
//

import Foundation
import Observation
import SharedLogic

@MainActor
@Observable
class GameViewModel {
    private let weaponResourceGetUseCase: WeaponResourceGetUseCaseInterface
    private var selectedWeaponId: Int?
    
    private(set) var loadedWeapons: [Weapon] = []
    private(set) var isLoading: Bool = false
    
    var currentWeapon: Weapon? {
        guard let selectedWeaponId = selectedWeaponId else {
            return nil
        }
        return loadedWeapons.first(where: { $0.id == selectedWeaponId })
    }
    
    init(weaponResourceGetUseCase: WeaponResourceGetUseCaseInterface) {
        self.weaponResourceGetUseCase = weaponResourceGetUseCase
    }
    
    func weaponSelected(id: Int) {
        selectedWeaponId = id
        
        // ロード済みの場合はスキップ
        if loadedWeapons.contains(where: { $0.id == id }) { return }
        
        Task {
            isLoading = true
            do {
                let weapon = try await weaponResourceGetUseCase.execute(id: Int32(id))
                loadedWeapons.append(weapon)
            } catch {
                print(error.localizedDescription)
            }
            isLoading = false
        }
    }
    
    func resetButtonTapped() {
        loadedWeapons.removeAll()
    }
}
