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
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.domain.entities.Weapon
import androidx.compose.ui.platform.LocalResources

@Composable
fun GameView(
    viewModel: GameViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val loadedWeaponsText: String = state.loadedWeapons.joinToString(separator = ", ") { it.name }

    Surface(
        color = Color.Black,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .safeContentPadding()
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

            ActionButtons(viewModel = viewModel)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun WeaponImage(name: String?, isLoading: Boolean) {
    Box(
        modifier = Modifier
            .background(Color.Green)
            .aspectRatio(
                ratio = 1f,
                matchHeightConstraintsFirst = true
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (name != null) {
            Icon(
                painter = painterResource(
                    id = LocalResources.current.getIdentifier(
                        name,
                        "drawable",
                        LocalContext.current.packageName
                    )
                ),
                contentDescription = "Weapon Icon",
                tint = Color.White,
                modifier = Modifier.padding(24.dp)
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
        horizontalAlignment = Alignment.Start,
    ) {
        WeaponImage(name = weapon?.imageName, isLoading = isLoading)

        Spacer(modifier = Modifier.height(12.dp))

        SpecText(title = "Name : ", value = weapon?.name ?: " - ")

        Spacer(modifier = Modifier.height(4.dp))

        SpecText(title = "Capacity : ", value = capacityText)
    }
}

@Composable
fun ActionButton(
    title: String,
    modifier: Modifier,
    action: (() -> Unit)
) {
    Button(
        onClick = action,
        modifier = modifier
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
        ),
        shape = RoundedCornerShape(size = 10.dp)
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
fun ActionButtons(viewModel: GameViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            ActionButton(
                title = "Pistol",
                modifier = Modifier.weight(1f),
            ) {
                viewModel.weaponSelected(id = 0)
            }

            Spacer(modifier = Modifier.width(12.dp))

            ActionButton(
                title = "Bazooka",
                modifier = Modifier.weight(1f),
            ) {
                viewModel.weaponSelected(id = 1)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ActionButton(
            title = "Reset",
            modifier = Modifier.fillMaxWidth(),
        ) {
            viewModel.resetButtonTapped()
        }
    }
}