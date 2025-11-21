@file:Suppress("ktlint:standard:class-signature", "ktlint:standard:blank-line-between-when-conditions")
@file:OptIn(ExperimentalForeignApi::class)

package dev.sdkforge.exif.data

import cnames.structs.__CFDictionary
import cnames.structs.__CFString
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPrimitiveVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.IntVarOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.value
import platform.CoreFoundation.CFDictionaryGetValue
import platform.CoreFoundation.CFNumberGetValue
import platform.CoreFoundation.CFNumberType
import platform.CoreFoundation.kCFNumberIntType
import platform.ImageIO.kCGImagePropertyOrientation
import platform.ImageIO.kCGImagePropertyPixelHeight
import platform.ImageIO.kCGImagePropertyPixelWidth
import platform.UIKit.UIImageOrientation

internal class ExifDataDecoder(
    private val imageSourceProperties: CPointer<__CFDictionary>?,
) : ExifData {

    override val imageWidth: Int? get() = readValue<Int>(imageSourceProperties, kCGImagePropertyPixelWidth)
    override val imageHeight: Int? get() = readValue<Int>(imageSourceProperties, kCGImagePropertyPixelHeight)

    override val orientation: Orientation
        get() {
            val orientation = readValue<Int>(imageSourceProperties, kCGImagePropertyOrientation) ?: return Orientation.Undefined

            return when (orientation.toLong()) {
                // The encoded image data matches the image’s intended display orientation
                UIImageOrientation.UIImageOrientationUp.value -> Orientation.Normal
                // The encoded image data is rotated 90° clockwise from the image’s intended display orientation
                UIImageOrientation.UIImageOrientationRight.value -> Orientation.Clockwise90
                // The encoded image data is rotated 180° from the image’s intended display orientation
                UIImageOrientation.UIImageOrientationDown.value -> Orientation.Clockwise180
                // The image has been rotated 90° counterclockwise from the orientation of its original pixel data.
                UIImageOrientation.UIImageOrientationLeft.value -> Orientation.Clockwise270
                // The encoded image data is horizontally flipped from the image’s intended display orientation
                UIImageOrientation.UIImageOrientationUpMirrored.value -> Orientation.FlipHorizontal
                // The encoded image data is vertically flipped from the image’s intended display orientation
                UIImageOrientation.UIImageOrientationDownMirrored.value -> Orientation.FlipVertical
                // The encoded image data is horizontally flipped and rotated 90° counter-clockwise from the image’s intended display orientation
                UIImageOrientation.UIImageOrientationLeftMirrored.value -> Orientation.Transpose
                // The encoded image data is horizontally flipped and rotated 90° clockwise from the image’s intended display orientation
                UIImageOrientation.UIImageOrientationRightMirrored.value -> Orientation.Transverse
                else -> Orientation.Undefined
            }
        }
}

private inline fun <reified VALUE> readValue(
    dict: CPointer<__CFDictionary>?,
    key: CPointer<__CFString>?,
): VALUE? = memScoped {
    val (value, type) = when (VALUE::class) {
        Int::class -> alloc<IntVar>() to kCFNumberIntType
        else -> return@memScoped null
    }

    val result = readValue(dict, key, value, type)

    when (result) {
        is IntVarOf<*> -> result.value as VALUE
        else -> null
    }
}

private fun readValue(
    dict: CPointer<__CFDictionary>?,
    key: CPointer<__CFString>?,
    value: CPrimitiveVar,
    type: CFNumberType,
): CPrimitiveVar? {
    val success = CFNumberGetValue(
        number = CFDictionaryGetValue(dict, key)?.reinterpret(),
        theType = type,
        valuePtr = value.ptr,
    )

    return if (success) value else null
}
