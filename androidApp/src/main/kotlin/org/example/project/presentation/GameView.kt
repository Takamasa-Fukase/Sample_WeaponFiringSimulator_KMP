package org.example.project.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.domain.entities.Weapon

@Composable
fun GameView(
    viewModel: GameViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val loadedWeaponsText: String = state.loadedWeapons.joinToString(separator = ", ") { it.name }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .background(Color.Black)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Row {
                Text(
                    "Loaded : [ $loadedWeaponsText ]",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green,
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            WeaponDisplayArea(
                weapon = state.currentWeapon,
                isLoading = state.isLoading,
            )

            Spacer(modifier = Modifier.weight(1f))

            actionButtons(viewModel = viewModel)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun WeaponImage(name: String?, isLoading: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
            .aspectRatio(
                ratio = 1f,
                matchHeightConstraintsFirst = true
            )
    ) {
        if (name != null) {
            Text(
                name,
                color = Color.White
            )

        } else {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)

            } else {
                Text(
                    "Select any weapon.",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SpecText(title: String, value: String) {
    Row {
        Text(
            title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            value,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green,
        )
    }
}

@Composable
fun WeaponDisplayArea(weapon: Weapon?, isLoading: Boolean) {
    val capacityText = weapon?.capacity?.let { "$it bullets" } ?: " - "
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        WeaponImage(name = weapon?.imageName, isLoading = isLoading)
        SpecText(title = "Name : ", value = weapon?.name ?: " - ")
        SpecText(title = "Capacity : ", value = capacityText)
    }
}

@Composable
fun actionButton(title: String, action: (() -> Unit)) {
    Button(
        onClick = action,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Text(
            title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }
}

@Composable
fun actionButtons(viewModel: GameViewModel) {
    Column {
        Row {
            actionButton(title = "Pistol") {
                viewModel.weaponSelected(id = 0)
            }
            actionButton(title = "Bazooka") {
                viewModel.weaponSelected(id = 1)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        actionButton(title = "Reset") {
            viewModel.resetButtonTapped()
        }
    }
}