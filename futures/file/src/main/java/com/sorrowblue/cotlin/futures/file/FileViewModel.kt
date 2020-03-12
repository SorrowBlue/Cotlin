package com.sorrowblue.cotlin.futures.file

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.MediaStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.sorrowblue.cotlin.domains.folder.Folder
import com.sorrowblue.cotlin.domains.folder.FolderRepository

internal class FileViewModel(
	val context: Context,
	val folder: Folder,
	val adapter: FileAdapter,
	private val repo: FolderRepository
) : ViewModel(),
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
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			adapter.setList(repo.reload(folder))
		}
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	private fun onDestroy() = context.contentResolver.registerContentObserver(
		MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, contentObserver
	)

	@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
	private fun onCreate() = context.contentResolver.unregisterContentObserver(contentObserver)

}