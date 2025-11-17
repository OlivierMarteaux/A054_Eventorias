package com.oliviermarteaux.localshared.firebase.firestore.utils

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Downloads an image from the internet and stores it in a temporary local file.
 * Returns a Uri that can be used with Firebase Storage putFile().
 */
suspend fun downloadImageToLocalFile(context: Context, imageUrl: String): Uri =
    withContext(Dispatchers.IO) {

        val url = URL(imageUrl)
        val connection = (url.openConnection() as HttpURLConnection).apply {
            doInput = true
            connect()
        }

        val inputStream = connection.inputStream

        // Create a temp file inside app cache dir
        val tempFile = File.createTempFile("downloaded_image_", ".jpg", context.cacheDir)

        FileOutputStream(tempFile).use { output ->
            inputStream.copyTo(output)
        }

        Uri.fromFile(tempFile)
    }