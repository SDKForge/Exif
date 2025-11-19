@file:Suppress("ktlint:standard:class-signature")

package dev.sdkforge.exif.data

import androidx.exifinterface.media.ExifInterface

internal class ExifDataDecoder(
    private val exif: ExifInterface,
) : ExifData {

    override val imageWidth: Int? get() = exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)?.toIntOrNull()
    override val imageHeight: Int? get() = exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)?.toIntOrNull()

    override val orientation: Orientation
        get() = when (exif.getAttribute(ExifInterface.TAG_ORIENTATION)?.toIntOrNull()) {
            ExifInterface.ORIENTATION_NORMAL -> Orientation.Normal
            ExifInterface.ORIENTATION_ROTATE_90 -> Orientation.Clockwise90
            ExifInterface.ORIENTATION_ROTATE_180 -> Orientation.Clockwise180
            ExifInterface.ORIENTATION_ROTATE_270 -> Orientation.Clockwise270
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> Orientation.FlipHorizontal
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> Orientation.FlipVertical
            ExifInterface.ORIENTATION_TRANSPOSE -> Orientation.Transpose
            ExifInterface.ORIENTATION_TRANSVERSE -> Orientation.Transverse
            ExifInterface.ORIENTATION_UNDEFINED -> Orientation.Undefined
            else -> Orientation.Undefined
        }
}
