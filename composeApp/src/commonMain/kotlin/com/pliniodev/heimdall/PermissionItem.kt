package com.pliniodev.heimdall

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pliniodev.heimdall.permissions.model.PermissionState

@Composable
internal fun PermissionItem(
    permissionName: String,
    permissionState: PermissionState,
    onRequestClick: () -> Unit,
    onOpenSettingsClick: () -> Unit,
) {
    val (stateTitle, stateColor) = remember(permissionState) {
        when (permissionState) {
            PermissionState.GRANTED -> "Granted" to Color.Green
            PermissionState.NOT_DETERMINED -> "Not determined" to Color.Gray
            PermissionState.DENIED -> "Denied" to Color.Red
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = permissionName,
                color = Color.Black,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = stateTitle,
                color = stateColor,
            )
            Button(
                onClick = onOpenSettingsClick,
            ) {
                Text(
                    text = "Settings",
                    color = MaterialTheme.colors.onPrimary,
                )
            }
        }
        AnimatedVisibility(permissionState.notGranted()) {
            Button(
                onClick = onRequestClick,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Request permission",
                    color = MaterialTheme.colors.onPrimary,
                )
            }
        }
    }
}