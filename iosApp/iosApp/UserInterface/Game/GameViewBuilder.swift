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
        let viewModel = GameViewModel(
            presenter: Factory.create()
        )
        return GameView(viewModel: viewModel)
    }
}
