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
        let dataSource = Factory.create()
        let presenter = SharedFactory.shared.createGamePresenter(
            weaponDataSource: dataSource,
            coroutineScope: IosMainScope.companion.createIosMainScope()
        )
        let viewModel = GameViewModel(
            presenter: presenter
        )
        return GameView(viewModel: viewModel)
    }
}
