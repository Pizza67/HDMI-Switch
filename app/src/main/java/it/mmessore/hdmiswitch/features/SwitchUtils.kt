package it.mmessore.hdmiswitch.features

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.MATCH_ALL
import android.os.Build
import android.os.SystemClock
import android.util.Log
import it.mmessore.hdmiswitch.data.HdmiSwitchPreferences

object SwitchUtils {
    private val startHdmiIntent by lazy { createStartHdmiIntent() }

    fun isDeviceSupported(context: Context): Boolean =
        context.packageManager.queryIntentActivities(startHdmiIntent, MATCH_ALL).isNotEmpty()

    fun scheduleHdmiSwitch(context: Context) {
        val preferences = HdmiSwitchPreferences(context)
        if (preferences.isHdmiSwitchAtBootEnabled()) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val delay = preferences.getHdmiSwitchDelay()
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                startHdmiIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val triggerAtMillis = SystemClock.elapsedRealtime() + delay + 100
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerAtMillis,
                        pendingIntent
                    )
                } else {
                    Log.e("BootCompletedReceiver", "Cannot schedule exact alarms")
                }
            } else {
                alarmManager.setExact(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            }
        }
    }

    private fun createStartHdmiIntent(): Intent {
        val intent = Intent()
        intent.component = ComponentName(
            "com.softwinner.awlivetv",
            "com.softwinner.awlivetv.MainActivity"
        )
        intent.putExtra("input_source", "HDMI1")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        return intent
    }

}