package com.wannaphong.hostai

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

/**
 * Startet den API-Server automatisch:
 * - nach dem Hochfahren (BOOT_COMPLETED / LOCKED_BOOT_COMPLETED nach dem Entsperren),
 * - nach einem App-Update (MY_PACKAGE_REPLACED),
 * - auf Zuruf von außen (z. B. Wächter-Skript):
 *   am broadcast -a com.florian.hostai.lokal.START -n com.florian.hostai.lokal/com.wannaphong.hostai.AutostartReceiver
 * Port und Modell kommen aus den gespeicherten Einstellungen (siehe ApiServerService).
 */
class AutostartReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        LogManager.i("AutostartReceiver", "Autostart ausgelöst: ${intent.action}")
        val start = Intent(context, ApiServerService::class.java).apply {
            action = ApiServerService.ACTION_START
        }
        try {
            ContextCompat.startForegroundService(context, start)
        } catch (e: Exception) {
            LogManager.e("AutostartReceiver", "Autostart fehlgeschlagen", e)
        }
    }
}
