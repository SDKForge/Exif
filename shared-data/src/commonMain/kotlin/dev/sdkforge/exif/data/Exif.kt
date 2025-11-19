@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.exif.data

expect object Exif {
    fun parse(path: String?): ExifData?
}
