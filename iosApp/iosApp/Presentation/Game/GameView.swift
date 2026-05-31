//
//  GameView.swift
//  iosApp
//
//  Created by ウルトラ深瀬 on 2026/05/31.
//

import SwiftUI
import SharedLogic

struct GameView: View {
    @State var viewModel: GameViewModel = GameViewModel(weaponResourceGetUseCase: WeaponResourceGetUseCase(weaponRepository: WeaponRepository(weaponDataSource: WeaponDataSource())))
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer().frame(height: 12)
            
            HStack {
                Text("Loaded : [ \(loadedWeaponsText) ]")
                    .font(.system(size: 22, weight: .bold))
                
                Spacer()
            }
            
            Spacer().frame(height: 16)
            
            weaponDisplayArea(viewModel.currentWeapon)
            
            Spacer()
            
            actionButtons
            
            Spacer().frame(height: 32)
        }
        .padding(.horizontal, 32)
        .background(.black)
        .foregroundStyle(.green)
    }
    
    var loadedWeaponsText: String {
        return viewModel.loadedWeapons.map(\.name).joined(separator: ", ")
    }
    
    @ViewBuilder
    func weaponImage(name: String?) -> some View {
        Group {
            if let name = name {
                Image(name)
                    .resizable()
                    .renderingMode(.template)
                    .padding(24)
            } else {
                if viewModel.isLoading {
                    ProgressView()
                        .progressViewStyle(.circular)
                        .tint(.white)
                        .scaleEffect(2)
                    
                } else {
                    Text("Select any weapon.")
                        .font(.system(size: 28, weight: .bold))
                }
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(.green)
        .foregroundStyle(.white)
        .aspectRatio(1, contentMode: .fit)
    }
    
    func specText(title: String, value: String) -> some View {
        HStack {
            Text(title)
                .font(.system(size: 28, weight: .bold))
            
            Spacer()
            
            Text(value)
                .font(.system(size: 28, weight: .bold))
        }
    }
    
    func weaponDisplayArea(_ weapon: Weapon?) -> some View {
        let capacityText: String = {
            if let capacity = weapon?.capacity {
                return "\(capacity) bullets"
            } else {
                return " - "
            }
        }()
        return VStack(alignment: .leading) {
            weaponImage(name: weapon?.imageName)
                .aspectRatio(1, contentMode: .fit)
            
            specText(title: "Name : ", value: weapon?.name ?? " - ")
            specText(title: "Capacity : ", value: capacityText)
        }
    }
    
    func actionButton(title: String, action: @escaping () -> Void) -> some View {
        Button {
            action()
        } label: {
            Text(title)
                .font(.system(size: 24, weight: .bold))
                .padding()
                .foregroundStyle(.black)
                .frame(maxWidth: .infinity)
                .frame(height: 52)
                .background(.white)
                .clipShape(RoundedRectangle(cornerRadius: 10))
        }
    }
    
    var actionButtons: some View {
        VStack(spacing: 0){
            HStack(spacing: 0){
                actionButton(title: "Pistol") {
                    viewModel.weaponSelected(id: 0)
                }
                
                Spacer().frame(width: 12)
                
                actionButton(title: "Bazooka") {
                    viewModel.weaponSelected(id: 1)
                }
            }
            
            Spacer().frame(height: 12)
                      
            actionButton(title: "Reset") {
                viewModel.resetButtonTapped()
            }
        }
    }
}

#Preview {
    GameView()
}
