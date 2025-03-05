package it.mmessore.hdmiswitch.data

import android.content.Context
import android.content.SharedPreferences

class HdmiSwitchPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("HDMISwitchPreferences", Context.MODE_PRIVATE)

    companion object {
        const val HDMI_SWITCH_AT_BOOT_ENABLED = "hdmi_switch_at_boot_enabled"
        const val HDMI_SWITCH_DELAY = "hdmi_switch_delay"
    }

    fun isHdmiSwitchAtBootEnabled(): Boolean {
        return sharedPreferences.getBoolean(HDMI_SWITCH_AT_BOOT_ENABLED, false)
    }
    fun setHdmiSwitchAtBootEnabled(enabled: Boolean){
        sharedPreferences.edit().putBoolean(HDMI_SWITCH_AT_BOOT_ENABLED, enabled).apply()
    }
    fun getHdmiSwitchDelay(): Int = sharedPreferences.getInt(HDMI_SWITCH_DELAY, 0)
    fun setHdmiSwitchDelay(delay: Int){sharedPreferences.edit().putInt(HDMI_SWITCH_DELAY, delay).apply()}
}

