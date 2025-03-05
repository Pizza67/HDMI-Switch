package it.mmessore.hdmiswitch.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import it.mmessore.hdmiswitch.R
import it.mmessore.hdmiswitch.data.HdmiSwitchPreferences
import it.mmessore.hdmiswitch.data.SettingsMenuData
import it.mmessore.hdmiswitch.features.SwitchUtils
import it.mmessore.hdmiswitch.ui.composables.*

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val preferences = HdmiSwitchPreferences(context)
    var enableSwitchOnBoot by remember { mutableStateOf(preferences.isHdmiSwitchAtBootEnabled()) }
    var delayBeforeSwitchingOnHDMI by remember { mutableStateOf(preferences.getHdmiSwitchDelay().toString()) }

    Row(modifier = modifier.fillMaxSize()) {
        Header(modifier = Modifier.weight(.8f))
        Spacer(modifier = Modifier.width(100.dp))
        SettingsMenu(
            enableSwitchOnBoot = enableSwitchOnBoot,
            delayBeforeSwitchingOnHDMI = delayBeforeSwitchingOnHDMI,
            onEnableSwitchOnBootChange = { enableSwitchOnBoot = canEnableSwitchOnBoot(it, preferences, context) },
            onDelayBeforeSwitchingOnHDMIChange = {
                preferences.setHdmiSwitchDelay(it.toInt())
                delayBeforeSwitchingOnHDMI = it
                                                 },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.2f))
                .weight(1f)
        )
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxHeight()
    ) {
        Text(
            text = "HDMI Switch",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(color = Color(0xFFE0FBFC), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.twotone_input_hdmi_24),
                contentDescription = "HDMI Switch logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(250.dp)
            )
        }
    }
}

@Composable
fun SettingsMenu(
    enableSwitchOnBoot: Boolean,
    delayBeforeSwitchingOnHDMI: String,
    onEnableSwitchOnBootChange: (Boolean) -> Unit,
    onDelayBeforeSwitchingOnHDMIChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val delayItems = SettingsMenuData().getDelayItems(LocalContext.current)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .width(200.dp)
            .padding(16.dp)
    )
    {
        item {
            Text(
                text = stringResource(R.string.settings),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
            )
        }
        item {
           SwitchSetting(
               settingName = stringResource(id = R.string.enable_switch_on_boot),
               checked = enableSwitchOnBoot,
               onCheckedChange = { onEnableSwitchOnBootChange(it) }
           )
        }
        item {
            SettingLabel(stringResource(id = R.string.delay_before_switching_on_hdmi))
        }
        items(delayItems.size) {
            val item = delayItems[it]
            RadioSetting(
                name = item.name,
                value = item.value,
                isEnabled = enableSwitchOnBoot,
                isSelected = item.value == delayBeforeSwitchingOnHDMI,
                onSelect = { delay -> onDelayBeforeSwitchingOnHDMIChange(delay) }
            )
        }
    }
}

/**
 * Checks if enabling a switch on boot is supported and handles the result.
 *
 * @param enableSwitchOnBoot True if the switch should be enabled on boot, false otherwise.
 * @param context The application context.
 * @return True if the switch can be enabled on boot (and it's requested), false otherwise.
 */
fun canEnableSwitchOnBoot(
    enableSwitchOnBoot: Boolean,
    hdmiSwitchPreferences: HdmiSwitchPreferences,
    context: Context
): Boolean {
    var ret = false

    if (enableSwitchOnBoot && !SwitchUtils.isDeviceSupported(context)) {
        Toast.makeText(context, "Device not supported", Toast.LENGTH_LONG).show()
    } else {
        ret = enableSwitchOnBoot
    }

    hdmiSwitchPreferences.setHdmiSwitchAtBootEnabled(ret)
    return ret
}
