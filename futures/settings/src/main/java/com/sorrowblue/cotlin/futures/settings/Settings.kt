package com.sorrowblue.cotlin.futures.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

object Settings {
	fun applyDarkMode(context: Context) {
		val key = context.getString(R.string.settings_key_dark_mode)
		val value = PreferenceManager.getDefaultSharedPreferences(context)?.getString(key, null)
			?: context.getString(R.string.settings_value_follow_system)
		applyDarkMode(context, value)
	}

	internal fun applyDarkMode(context: Context, value: String) {
		when (value) {
			context.getString(R.string.settings_value_follow_system) -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
			context.getString(R.string.settings_value_auto_battery) -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
			context.getString(R.string.settings_value_no) -> AppCompatDelegate.MODE_NIGHT_NO
			context.getString(R.string.settings_value_yes) -> AppCompatDelegate.MODE_NIGHT_YES
			else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
		}.also(AppCompatDelegate::setDefaultNightMode)
	}
}
