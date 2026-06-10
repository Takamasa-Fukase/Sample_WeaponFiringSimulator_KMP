//
//  GamePresenter.swift
//  iosApp
//
//  Created by ウルトラ深瀬 on 2026/06/10.
//

import Foundation
import SharedLogic
import Combine

class GamePresenter {
    private let weaponResourceGetUseCase: WeaponResourceGetUseCaseInterface
    
    private let selectedWeaponIdSubject = CurrentValueSubject<Int?, Never>(nil)
    let loadedWeaponsSubject = CurrentValueSubject<[Weapon], Never>([])
    let isLoadingSubject = CurrentValueSubject<Bool, Never>(false)
    let currentWeaponPublisher: AnyPublisher<Weapon?, Never>

    init(
        weaponResourceGetUseCase: WeaponResourceGetUseCaseInterface
    ) {
        self.weaponResourceGetUseCase = weaponResourceGetUseCase
        currentWeaponPublisher = selectedWeaponIdSubject.combineLatest(loadedWeaponsSubject)
            .map { (id, weapons) in
                return weapons.first(where: { $0.id == id ?? 0 })
            }
            .share()
            .eraseToAnyPublisher()
    }
    
    func weaponSelected(id: Int) {
        selectedWeaponIdSubject.send(id)
        
        // ロード済みの場合はスキップ
        if loadedWeaponsSubject.value.contains(where: { $0.id == id }) { return }
                
        Task {
            isLoadingSubject.send(true)
            do {
                let weapon = try await weaponResourceGetUseCase.execute(id: Int32(id))
                let appendedList = loadedWeaponsSubject.value + [weapon]
                loadedWeaponsSubject.send(appendedList)
            } catch {
                print(error.localizedDescription)
            }
            isLoadingSubject.send(false)
        }
    }
    
    func resetButtonTapped() {
        loadedWeaponsSubject.send([])
    }
}
