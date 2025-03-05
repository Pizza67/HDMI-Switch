package it.mmessore.hdmiswitch.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ClickableSurfaceColors
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.ClickableSurfaceScale
import androidx.tv.material3.ClickableSurfaceShape
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.MaterialTheme.colorScheme
import androidx.tv.material3.RadioButton
import androidx.tv.material3.ShapeDefaults
import androidx.tv.material3.Surface
import androidx.tv.material3.Switch
import androidx.tv.material3.Text

data class DelayItemModel(
    val name: String,
    val value: String
)

@Composable
fun SwitchSetting(
    settingName: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    SettingsItem(
        onClick = { onCheckedChange(!checked) },
        modifier = modifier
    ) {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = settingName,
            modifier = Modifier.padding(16.dp)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun RadioSetting(
    name: String,
    value: String,
    isSelected: Boolean,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = false
) {
    SettingsItem(
        onClick = { onSelect(value) },
        modifier = modifier,
        enabled = isEnabled
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = name,
            modifier = Modifier.padding(16.dp)
        )
        RadioButton(
            selected = isSelected,
            enabled = isEnabled,
            onClick = { onSelect(value) },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun SettingLabel(label: String) {
    Text(
        style = MaterialTheme.typography.titleMedium,
        text = label,
        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
    )
}

@Composable
fun SettingsItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: ClickableSurfaceShape = ClickableSurfaceDefaults.shape(
        shape = ShapeDefaults.Small,
        focusedShape = ShapeDefaults.Small
    ),
    color: ClickableSurfaceColors = ClickableSurfaceDefaults.colors(
        containerColor = colorScheme.tertiary,
        focusedContainerColor = colorScheme.onSurface,
        contentColor = colorScheme.onSurface,
        focusedContentColor = colorScheme.tertiary
    ),
    scale: ClickableSurfaceScale = ClickableSurfaceDefaults.scale(focusedScale = 1.1f),
    content: @Composable (RowScope.() -> Unit)
) {
    Surface(
        onClick = { onClick() },
        colors = color,
        shape = shape,
        scale = scale,
        enabled = enabled,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            content()
        }
    }
}