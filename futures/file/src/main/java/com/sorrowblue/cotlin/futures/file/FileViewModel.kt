package com.sorrowblue.cotlin.futures.file

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sorrowblue.cotlin.domains.folder.Folder
import com.sorrowblue.cotlin.domains.folder.FolderRepository
import kotlinx.coroutines.launch

internal class FileViewModel(
	private val context: Context,
	val folder: Folder,
	val adapter: FileAdapter,
	private val repo: FolderRepository
) : ViewModel(),
	SwipeRefreshLayout.OnRefreshListener,
	LifecycleObserver {

	private val contentObserver = object : ContentObserver(Handler()) {
		override fun onChange(selfChange: Boolean) {
			super.onChange(selfChange)
			this.onChange(selfChange, null)
		}

		override fun onChange(selfChange: Boolean, uri: Uri?) {
			refresh()
		}
	}

	init {
		adapter.setList(folder.child)
	}

	fun refresh() {
		isLoading.postValue(true)
		viewModelScope.launch {
			adapter.currentList = repo.reload(folder)
			isLoading.postValue(false)
		}
	}

	val isLoading = MutableLiveData(false)


	override fun onRefresh() = refresh()

	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	private fun onDestroy() = context.contentResolver.registerContentObserver(
		MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, contentObserver
	)

	@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
	private fun onCreate() = context.contentResolver.unregisterContentObserver(contentObserver)

}