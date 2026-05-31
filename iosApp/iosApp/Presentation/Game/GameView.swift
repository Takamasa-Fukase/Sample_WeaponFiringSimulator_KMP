//
//  GameView.swift
//  iosApp
//
//  Created by ウルトラ深瀬 on 2026/05/31.
//

import SwiftUI
import SharedLogic

struct GameView: View {
    @State var loadedWeapons: [Weapon] = []
    @State var selectedWeaponId: Int? = 1
    var currentWeapon: Weapon? {
        guard let selectedWeaponId = selectedWeaponId else {
            return nil
        }
        return loadedWeapons.first(where: { $0.id == selectedWeaponId })
    }
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer().frame(height: 32)
            
            HStack {
                Text("Loaded Weapons : [ \(loadedWeaponsText) ]")
                    .font(.system(size: 16, weight: .bold))
                
                Spacer()
            }
            
            Spacer().frame(height: 16)
            
            weaponDisplayArea(currentWeapon)
            
            Spacer()
            
            HStack {
                actionButton(title: "Pistol") {
                    
                }
                
                actionButton(title: "Bazooka") {
                    
                }
            }
                      
            actionButton(title: "Reset") {
                
            }
            
            Spacer()
        }
        .padding(.horizontal, 32)
        .background(.black)
        .foregroundStyle(.green)
        .task {
            let useCase = WeaponResourceGetUseCase(weaponRepository: WeaponRepository(weaponDataSource: WeaponDataSource()))
            do {
                let weapon = try await useCase.execute(id: 1)
                loadedWeapons.append(weapon)
            } catch {
                print(error.localizedDescription)
            }
        }
    }
    
    var loadedWeaponsText: String {
        return loadedWeapons.map(\.name).joined(separator: ", ")
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
                Text("Select any weapon.")
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
                .foregroundStyle(.white)
            
        }
    }
}

#Preview {
    GameView()
}
