package com.sorrowblue.cotlin.futures.folder

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.MediaStore
import androidx.lifecycle.*
import com.sorrowblue.cotlin.domains.folder.FolderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class FolderViewModel(val adapter: FolderAdapter, private val context: Context, private val repo: FolderRepository) : ViewModel(), LifecycleObserver {

	val isLoading = MutableLiveData(false)

	init {
		refresh()
	}
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
		isLoading.postValue(true)
		viewModelScope.launch {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				adapter.currentList = repo.getAll()
			}
			isLoading.postValue(false)
		}
	}


	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	private fun onDestroy() = context.contentResolver.registerContentObserver(
		MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, contentObserver
	)

	@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
	private fun onCreate() = context.contentResolver.unregisterContentObserver(contentObserver)

}