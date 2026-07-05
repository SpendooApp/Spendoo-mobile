package com.spendoo.categories.presentation

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import org.koin.core.context.GlobalContext

actual fun platform() = "Android"

actual fun openAppSettings() {
    val context = GlobalContext.get().get<android.content.Context>()
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}

actual fun compressImage(
    imageBytes: ByteArray,
    maxWidth: Int,
    maxHeight: Int,
    quality: Int
): ByteArray {
    return try {
        val options = android.graphics.BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size, options)

        var inSampleSize = 1
        val outWidth = options.outWidth
        val outHeight = options.outHeight

        if (outHeight > maxHeight || outWidth > maxWidth) {
            val halfHeight = outHeight / 2
            val halfWidth = outWidth / 2
            while ((halfHeight / inSampleSize) >= maxHeight && (halfWidth / inSampleSize) >= maxWidth) {
                inSampleSize *= 2
            }
        }

        val decodeOptions = android.graphics.BitmapFactory.Options().apply {
            this.inSampleSize = inSampleSize
        }
        var bitmap = android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size, decodeOptions)
            ?: return imageBytes

        // Scale the bitmap exactly to fit within maxWidth and maxHeight
        if (bitmap.width > maxWidth || bitmap.height > maxHeight) {
            val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
            val newWidth: Int
            val newHeight: Int
            if (bitmap.width > bitmap.height) {
                newWidth = maxWidth
                newHeight = (maxWidth / aspectRatio).toInt()
            } else {
                newHeight = maxHeight
                newWidth = (maxHeight * aspectRatio).toInt()
            }
            val scaledBitmap = android.graphics.Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
            if (scaledBitmap != bitmap) {
                bitmap.recycle()
                bitmap = scaledBitmap
            }
        }

        val outputStream = java.io.ByteArrayOutputStream()
        bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, quality, outputStream)
        val compressedBytes = outputStream.toByteArray()
        bitmap.recycle()
        compressedBytes
    } catch (e: Exception) {
        imageBytes
    }
}