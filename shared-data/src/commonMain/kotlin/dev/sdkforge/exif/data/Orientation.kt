package dev.sdkforge.exif.data

sealed interface Orientation {
    data object Normal : Orientation
    data object Clockwise90 : Orientation
    data object Clockwise180 : Orientation
    data object Clockwise270 : Orientation
    data object FlipHorizontal : Orientation
    data object FlipVertical : Orientation
    data object Transpose : Orientation
    data object Transverse : Orientation
    data object Undefined : Orientation
}
