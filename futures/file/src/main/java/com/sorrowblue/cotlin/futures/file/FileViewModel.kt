package com.sorrowblue.cotlin.futures.file

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import androidx.lifecycle.*
import com.sorrowblue.cotlin.domains.folder.Folder
import com.sorrowblue.cotlin.domains.folder.FolderRepository
import com.sorrowblue.cotlin.ui.lifecycle.NonNullLiveData
import kotlinx.coroutines.launch

internal class FileViewModel(
	val folder: Folder,
	val adapter: FileAdapter,
	private val context: Context,
	private val repo: FolderRepository
) : ViewModel(), LifecycleObserver {

	val isEmpty = NonNullLiveData(false)
	val isLoading = MutableLiveData(false)
	private val contentObserver = object : ContentObserver(Handler()) {
		override fun onChange(selfChange: Boolean) = onChange(selfChange, null)
		override fun onChange(selfChange: Boolean, uri: Uri?) = refresh()
	}

	init {
		refresh()
	}

	fun refresh() {
		isLoading.value = true
		viewModelScope.launch {
			adapter.currentList = repo.reload(folder)
			isEmpty.postValue(adapter.currentList.isEmpty())
			isLoading.postValue(false)
		}
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
	private fun onCreate() = context.contentResolver
		.registerContentObserver(EXTERNAL_CONTENT_URI, true, contentObserver)

	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	private fun onDestroy() = context.contentResolver.unregisterContentObserver(contentObserver)

}