package com.sorrowblue.cotlin.futures.folder

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import androidx.lifecycle.*
import com.sorrowblue.cotlin.domains.folder.FolderRepository
import kotlinx.coroutines.launch

internal class FolderViewModel(
	val adapter: FolderAdapter,
	private val context: Context,
	private val repo: FolderRepository
) : ViewModel(), LifecycleObserver {

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
			adapter.currentList = repo.getAll()
			isLoading.postValue(false)
		}
	}


	@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
	private fun registerContentObserver() =
		context.contentResolver.registerContentObserver(EXTERNAL_CONTENT_URI, true, contentObserver)

	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	private fun unregisterContentObserver() =
		context.contentResolver.unregisterContentObserver(contentObserver)

}
