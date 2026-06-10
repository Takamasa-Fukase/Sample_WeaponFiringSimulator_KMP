//
//  GamePresenter.swift
//  iosApp
//
//  Created by ウルトラ深瀬 on 2026/06/10.
//

import Foundation
import SharedLogic

//@MainActor
class GamePresenter {
    private let weaponResourceGetUseCase: WeaponResourceGetUseCaseInterface
    private var selectedWeaponId: Int?
    private var loadedWeapons: [Weapon] = []
    private weak var viewModel: GameViewModel?
    
    var currentWeapon: Weapon? {
        guard let selectedWeaponId = selectedWeaponId else {
            return nil
        }
        return loadedWeapons.first(where: { $0.id == selectedWeaponId })
    }

    init(
        weaponResourceGetUseCase: WeaponResourceGetUseCaseInterface,
        viewModel: GameViewModel
    ) {
        self.weaponResourceGetUseCase = weaponResourceGetUseCase
        self.viewModel = viewModel
    }
    
    func weaponSelected(id: Int) {
        selectedWeaponId = id
//        // VMを更新
//        viewModel?.setCurrentWeapon(
//            loadedWeapons.first(where: { $0.id == selectedWeaponId ?? 0 })
//        )
        
        // ロード済みの場合はスキップ
        if loadedWeapons.contains(where: { $0.id == id }) { return }
        
        Task {
            // VMを更新
            viewModel?.setIsLoading(true)
            do {
                let weapon = try await weaponResourceGetUseCase.execute(id: Int32(id))
                loadedWeapons.append(weapon)
                // VMを更新
                viewModel?.setLoadedWeapons(loadedWeapons)
            } catch {
                print(error.localizedDescription)
            }
            // VMを更新
            viewModel?.setIsLoading(false)
        }
    }
    
    func resetButtonTapped() {
        loadedWeapons.removeAll()
        // VMを更新
        viewModel?.setLoadedWeapons(loadedWeapons)
    }
}
