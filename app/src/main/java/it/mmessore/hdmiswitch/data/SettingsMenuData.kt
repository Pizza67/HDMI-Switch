package it.mmessore.hdmiswitch.data

import android.content.Context
import it.mmessore.hdmiswitch.R
import it.mmessore.hdmiswitch.ui.composables.DelayItemModel

class SettingsMenuData {
    fun getDelayItems(context: Context): List<DelayItemModel> {
        return listOf(
            DelayItemModel(context.getString(R.string.no_delay), "0"),
            DelayItemModel(context.getString(R.string.s_delay, 3), "3000"),
            DelayItemModel(context.getString(R.string.s_delay, 5), "5000"),
            DelayItemModel(context.getString(R.string.s_delay, 10), "10000")
        )
    }
}