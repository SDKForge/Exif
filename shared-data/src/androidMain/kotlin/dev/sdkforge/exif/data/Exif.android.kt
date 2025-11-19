@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.exif.data

import android.content.Context
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.exifinterface.media.ExifInterface

actual data object Exif {

    actual fun parse(path: String?): ExifData? {
        path ?: return null

        val uri = path.toUri()

        val exifInterface: ExifInterface = when {
            uri.scheme == "file" -> ExifInterface(uri.toFile())
            else -> ExifInterface(path)
        }

        return ExifDataDecoder(exif = exifInterface)
    }

    fun parse(context: Context, path: String?): ExifData? {
        path ?: return null

        val uri = path.toUri()

        val exifInterface: ExifInterface = when {
            uri.scheme == "file" -> ExifInterface(uri.toFile())
            else -> ExifInterface(context.contentResolver.openInputStream(uri)!!)
        }

        return ExifDataDecoder(exif = exifInterface)
    }
}
