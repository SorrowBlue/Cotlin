package com.sorrowblue.cotlin.data.folder

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Images.Media
import androidx.core.content.ContentResolverCompat
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.sorrowblue.cotlin.domains.folder.Folder
import com.sorrowblue.cotlin.domains.folder.FolderRepository
import com.sorrowblue.cotlin.domains.image.Image
import java.io.File
import java.time.LocalDateTime

internal class FolderRepositoryImp(private val context: Context) : FolderRepository {
	private val projection = mutableListOf(
		Media._ID, Media.SIZE, Media.DISPLAY_NAME, Media.MIME_TYPE, Media.DATE_ADDED,
		Media.DATE_MODIFIED, Media.HEIGHT, Media.WIDTH
	).apply { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) add(Media.RELATIVE_PATH) }
		.toTypedArray()

	override fun reload(folder: Folder): List<Image> {
		val selection =
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) "${Media.RELATIVE_PATH} like ? " else "${Media.DATA} like ? "
		return ContentResolverCompat.query(
			context.contentResolver,
			Media.EXTERNAL_CONTENT_URI,
			projection,
			selection,
			arrayOf("${folder.path}%"),
			Media.DISPLAY_NAME,
			null
		)?.use {
			val folderList = mutableListOf<Image>()
			val api = ImageApi(context.contentResolver, it)
			while (it.moveToNext()) {
				val name = api.name ?: continue
				val size = api.size ?: continue
				val contentUri = api.contentUri ?: continue
				val image = Image(
					contentUri,
					name,
					api.mimeType!!,
					size,
					LocalDateTime.now(),
					LocalDateTime.now()
				)
				folderList.add(image)
			}
			folderList
		} ?: emptyList()
	}

	private fun extractImage(cursor: Cursor): List<Folder> {
		val folderList = mutableListOf<Folder>()
		val api = ImageApi(context.contentResolver, cursor)
		while (cursor.moveToNext()) {
			val name = api.name ?: continue
			val size = api.size ?: continue
			val contentUri = api.contentUri ?: continue
			val relativePath = api.relativePath(contentUri) ?: continue
			val image = Image(
				contentUri,
				name,
				api.mimeType!!,
				size,
				LocalDateTime.now(),
				LocalDateTime.now()
			)
			folderList.find { it.path == relativePath }?.let { it.child += image }
				?: kotlin.run {
					folderList.add(
						Folder(
							relativePath,
							relativePath,
							mutableListOf(image)
						)
					)
				}
		}
		return folderList
	}

	override suspend fun getAll(): List<Folder> {
		return ContentResolverCompat.query(
			context.contentResolver,
			Media.EXTERNAL_CONTENT_URI,
			projection,
			null,
			null,
			Media.DISPLAY_NAME,
			null
		)?.use(this::extractImage) ?: emptyList()
	}
}


class ImageApi(private val contentResolver: ContentResolver, private val cursor: Cursor) {
	private val projection =
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) arrayOf(Media.DATA) else null
	private val relativePathColumn =
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) cursor.getColumnIndex(Media.RELATIVE_PATH) else null
	private val idColumn = cursor.getColumnIndexOrThrow(Media._ID)
	private val nameColumn = cursor.getColumnIndexOrThrow(Media.DISPLAY_NAME)
	private val sizeColumn = cursor.getColumnIndexOrThrow(Media.SIZE)
	private val mimeTypeColumn = cursor.getColumnIndexOrThrow(Media.MIME_TYPE)

	fun relativePath(uri: Uri): String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
		relativePathColumn?.let(cursor::getStringOrNull)
	} else {
		ContentResolverCompat.query(contentResolver, uri, projection, null, null, null, null)?.use {
			it.moveToFirst()
			it.getStringOrNull(0)
		}?.let { File(it) }?.parent
	}

	val mimeType get() = cursor.getStringOrNull(mimeTypeColumn)

	private val id get() = cursor.getLongOrNull(idColumn)
	val name get() = cursor.getStringOrNull(nameColumn)
	val size get() = cursor.getLongOrNull(sizeColumn)
	val contentUri
		get() = id?.let {
			ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, it)
		}
}