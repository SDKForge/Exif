@file:Suppress("ktlint:standard:function-signature")
@file:OptIn(ExperimentalForeignApi::class)

package dev.sdkforge.exif.data

import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreFoundation.CFStringCreateWithCString
import platform.CoreFoundation.CFStringGetSystemEncoding
import platform.CoreFoundation.CFURLCreateWithString
import platform.ImageIO.CGImageSourceCopyPropertiesAtIndex
import platform.ImageIO.CGImageSourceCreateWithURL

actual data object Exif {

    actual fun parse(path: String?): ExifData? {
        val url = CFURLCreateWithString(
            allocator = null,
            URLString = CFStringCreateWithCString(
                alloc = null,
                cStr = path.toString(),
                encoding = CFStringGetSystemEncoding(),
            ),
            baseURL = null,
        )

        val imageSource = CGImageSourceCreateWithURL(
            url = url,
            options = null,
        )

        val properties = CGImageSourceCopyPropertiesAtIndex(
            isrc = imageSource,
            index = 0.toULong(),
            options = null,
        )

        return ExifDataDecoder(imageSourceProperties = properties)
    }
}
