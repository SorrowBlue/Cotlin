package com.sorrowblue.cotlin.futures.settings

import android.os.Bundle
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.transition.MaterialSharedAxis
import com.sorrowblue.cotlin.ui.view.applyVerticalInsets

internal class SettingsFragment : PreferenceFragmentCompat() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
		exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
	}

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.settings_preferences_main, rootKey)
	}

	override fun onBindPreferences() {
		findPreference<ListPreference>(getString(R.string.settings_key_dark_mode))?.setOnPreferenceChangeListener { _, newValue ->
			Settings.applyDarkMode(
				requireContext(),
				newValue as? String ?: return@setOnPreferenceChangeListener false
			)
			true
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		view.applyVerticalInsets()
	}
}