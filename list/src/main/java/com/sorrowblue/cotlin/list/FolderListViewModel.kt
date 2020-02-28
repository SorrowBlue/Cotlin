package com.sorrowblue.cotlin.list

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.MediaStore.Images.Media
import androidx.core.content.ContentResolverCompat
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import androidx.lifecycle.*
import kotlinx.coroutines.launch


internal class FolderListViewModel(
	private val context: Context,
	val adapter: FolderListAdapter
) : ViewModel(), LifecycleObserver {

	val isLoading = MutableLiveData(false)
	private val contentObserver = object : ContentObserver(Handler()) {
		override fun onChange(selfChange: Boolean) {
			super.onChange(selfChange)
			this.onChange(selfChange, null)
		}

		override fun onChange(selfChange: Boolean, uri: Uri?) {
			refresh()
		}
	}

	fun refresh() {
		isLoading.value = true
		val projection = mutableListOf(Media._ID, Media.SIZE, Media.DISPLAY_NAME)
			.apply { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) add(Media.RELATIVE_PATH) }
			.toTypedArray()
		viewModelScope.launch {
			context.contentResolver.query(
				Media.EXTERNAL_CONTENT_URI, projection, null, null, Media.DISPLAY_NAME
			)?.use {
				val api = ImageApi(context.contentResolver, it)
				while (it.moveToNext()) {
					val name = api.name ?: continue
					val size = api.size ?: continue
					val contentUri = api.contentUri ?: continue
					val relativePath = api.relativePath(contentUri) ?: continue
					adapter.add(relativePath, Image(contentUri, name, size))
				}
				isLoading.postValue(false)
			}
		}
	}

	init {
		refresh()
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	private fun onDestroy() = context.contentResolver.registerContentObserver(
		Media.EXTERNAL_CONTENT_URI, true, contentObserver
	)

	@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
	private fun onCreate() = context.contentResolver.unregisterContentObserver(contentObserver)

}

class ImageApi(private val contentResolver: ContentResolver, private val cursor: Cursor) {
	@Suppress("DEPRECATION")
	private val projection =
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) arrayOf(Media.DATA) else null
	private val relativePathColumn =
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) cursor.getColumnIndex(Media.RELATIVE_PATH) else null
	private val idColumn = cursor.getColumnIndexOrThrow(Media._ID)
	private val nameColumn = cursor.getColumnIndexOrThrow(Media.DISPLAY_NAME)
	private val sizeColumn = cursor.getColumnIndexOrThrow(Media.SIZE)

	fun relativePath(uri: Uri): String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
		relativePathColumn?.let(cursor::getStringOrNull)
	} else {
		ContentResolverCompat.query(contentResolver, uri, projection, null, null, null, null)
		contentResolver.query(uri, projection, null, null, null)?.use {
			it.moveToFirst()
			it.getStringOrNull(0)
		}
	}

	private val id get() = cursor.getLongOrNull(idColumn)
	val name get() = cursor.getStringOrNull(nameColumn)
	val size get() = cursor.getIntOrNull(sizeColumn)
	val contentUri
		get() = id?.let {
			ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, it)
		}
}
