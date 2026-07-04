package com.spendoo.categories.presentation

import kotlinx.cinterop.BetaInteropApi
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.Foundation.NSData
import platform.Foundation.create
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.readBytes
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.CoreGraphics.CGSizeMake
import kotlinx.cinterop.useContents

actual fun platform() = "iOS"

actual fun openAppSettings() {
    val settingsUrl = NSURL.URLWithString(UIApplicationOpenSettingsURLString)
    if (settingsUrl != null && UIApplication.sharedApplication.canOpenURL(settingsUrl)) {
        UIApplication.sharedApplication.openURL(settingsUrl)
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun compressImage(
    imageBytes: ByteArray,
    maxWidth: Int,
    maxHeight: Int,
    quality: Int
): ByteArray {
    return try {
        val nsData = imageBytes.usePinned { pinned ->
            NSData.create(bytes = pinned.addressOf(0), length = imageBytes.size.toULong())
        }
        val image = UIImage.imageWithData(nsData) ?: return imageBytes

        val originalWidth = image.size.useContents { width }
        val originalHeight = image.size.useContents { height }

        if (originalWidth <= maxWidth && originalHeight <= maxHeight) {
            val compressedData = UIImageJPEGRepresentation(image, quality.toDouble() / 100.0) ?: return imageBytes
            return compressedData.bytes?.readBytes(compressedData.length.toInt()) ?: imageBytes
        }

        var newWidth = originalWidth
        var newHeight = originalHeight
        val aspectRatio = originalWidth / originalHeight
        if (originalWidth > originalHeight) {
            newWidth = maxWidth.toDouble()
            newHeight = newWidth / aspectRatio
        } else {
            newHeight = maxHeight.toDouble()
            newWidth = newHeight * aspectRatio
        }

        UIGraphicsBeginImageContextWithOptions(CGSizeMake(newWidth, newHeight), false, 1.0)
        image.drawInRect(platform.CoreGraphics.CGRectMake(0.0, 0.0, newWidth, newHeight))
        val resizedImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()

        val finalImage = resizedImage ?: image
        val compressedData = UIImageJPEGRepresentation(finalImage, quality.toDouble() / 100.0) ?: return imageBytes

        compressedData.bytes?.readBytes(compressedData.length.toInt()) ?: imageBytes
    } catch (_: Exception) {
        imageBytes
    }
}