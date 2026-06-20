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
        let mainScope = IosCoroutineScopeKt.createMainScope()
        let presenter = SharedLogicFactory.shared.createGamePresenter(
            weaponDataSource: dataSource,
            coroutineScope: mainScope
        )
        let viewModel = GameViewModel(
            presenter: presenter,
            coroutineCancelHandler: { [weak mainScope] in
                IosCoroutineScopeKt.cancel(scope: mainScope)
            }
        )
        return GameView(viewModel: viewModel)
    }
}
