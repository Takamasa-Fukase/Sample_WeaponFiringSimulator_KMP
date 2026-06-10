//
//  GameViewBuilder.swift
//  iosApp
//
//  Created by ウルトラ深瀬 on 2026/05/31.
//

import SwiftUI
import SharedLogic

struct GameViewBuilder {
    static func build() -> some View {
        let dataSource = WeaponDataSource()
        let repository = WeaponRepository(weaponDataSource: dataSource)
        let useCase = WeaponResourceGetUseCase(weaponRepository: repository)
        let viewModel: GameViewModel = GameViewModel()
        let presenter = GamePresenter(
            weaponResourceGetUseCase: useCase,
            viewModel: viewModel
        )
        viewModel.inject(presenter: presenter)
        return GameView(viewModel: viewModel)
    }
}
