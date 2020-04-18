package com.sorrowblue.cotlin.futures.image

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import androidx.lifecycle.*
import com.sorrowblue.cotlin.domains.folder.Folder
import com.sorrowblue.cotlin.domains.folder.FolderRepository
import com.sorrowblue.cotlin.ui.lifecycle.NonNullLiveData
import com.sorrowblue.cotlin.ui.lifecycle.SingleLiveEvent
import kotlinx.coroutines.launch

internal class ImageViewModel(
	val adapter: ImagePagerAdapter,
	private val context: Context,
	private val folder: Folder,
	private val repo: FolderRepository
) : ViewModel(), LifecycleObserver {

	init {
		adapter.currentList = folder.child
	}

	val currentItem = NonNullLiveData(0)

	val action = SingleLiveEvent<Action>()

	fun setAction(action: Action) {
		this.action.value = action
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

	val image get() = adapter.currentList[currentItem.value]
	val isEmpty = NonNullLiveData(false)

	fun refresh() {
		viewModelScope.launch {
			adapter.currentList = repo.reload(folder).also {
				if (it.isEmpty()) {
					isEmpty.postValue(true)
					return@launch
				}
			}
			if (adapter.currentList.size > currentItem.value) {
				currentItem.postValue(currentItem.value)
			} else {
				currentItem.postValue(adapter.currentList.size - 1)
			}
		}
	}


	@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
	private fun onCreate() = context.contentResolver
		.registerContentObserver(EXTERNAL_CONTENT_URI, true, contentObserver)

	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	private fun onDestroy() = context.contentResolver.unregisterContentObserver(contentObserver)

	enum class Action { FAVORITE, EDIT, SHARE, OPEN, DELETE }
}