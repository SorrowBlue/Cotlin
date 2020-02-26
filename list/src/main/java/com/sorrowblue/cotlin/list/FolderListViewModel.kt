package com.sorrowblue.cotlin.list

import android.content.ContentUris
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
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

	private fun refresh() {
		isLoading.value = true
		viewModelScope.launch {
			context.contentResolver.query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				null,
				null,
				null,
				null
			).use {
				val idColumn = it?.getColumnIndex(MediaStore.Images.Media._ID)!!
				val nameColumn = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
				val sizeColumn = it.getColumnIndex(MediaStore.Images.Media.SIZE)
				val relativePathColumn = it.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH)
				adapter.currentList = emptyList()
				while (it.moveToNext()) {
					val id = it.getLong(idColumn)
					val name = it.getString(nameColumn)
					val size = it.getInt(sizeColumn)
					val relativePath = it.getString(relativePathColumn)
					val contentUri: Uri = ContentUris.withAppendedId(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						id
					)
					Log.d("APPAPP", "${relativePath}")
					adapter.currentList.sortedBy { it.name }.find { it.name == relativePath }?.let {
						it.child += Image(contentUri, name, size)
						adapter.notifyDataSetChanged()
					} ?: kotlin.run {
						adapter.currentList += Folder(
							relativePath,
							mutableListOf(Image(contentUri, name, size))
						)
					}
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
		MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, contentObserver
	)

	@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
	private fun onCreate() = context.contentResolver.unregisterContentObserver(contentObserver)

}
