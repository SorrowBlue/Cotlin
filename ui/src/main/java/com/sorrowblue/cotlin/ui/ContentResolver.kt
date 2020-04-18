package com.sorrowblue.cotlin.ui

import android.content.ContentResolver
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler


fun ContentResolver.registerOnContentChangeListener(
	uri: Uri,
	notifyForDescendants: Boolean,
	listener: (selfChange: Boolean, uri: Uri?) -> Unit
) = object : ContentObserver(Handler()) {
	override fun onChange(selfChange: Boolean) = onChange(selfChange, null)
	override fun onChange(selfChange: Boolean, uri: Uri?) = listener.invoke(selfChange, uri)
}.also { registerContentObserver(uri, notifyForDescendants, it) }
