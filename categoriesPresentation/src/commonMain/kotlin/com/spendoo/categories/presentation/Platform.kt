package com.spendoo.categories.presentation

expect fun platform(): String

expect fun openAppSettings()

expect fun compressImage(
    imageBytes: ByteArray,
    maxWidth: Int = 1080,
    maxHeight: Int = 1080,
    quality: Int = 80
): ByteArray