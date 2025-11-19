package dev.sdkforge.exif.data

interface ExifData {
    //region Image Dimensions

    // TAG_IMAGE_WIDTH & kCGImagePropertyPixelWidth
    val imageWidth: Int?

    // TAG_IMAGE_LENGTH & kCGImagePropertyPixelHeight
    val imageHeight: Int?

    //endregion

    // Orientation

    // TAG_ORIENTATION & kCGImagePropertyOrientation
    val orientation: Orientation
}
