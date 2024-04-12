package com.sitaram.foodshare.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.sitaram.foodshare.theme.blue
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.green
import com.sitaram.foodshare.theme.primary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object ConverterUtil {

    fun convertUriToFile(context: Context, uri: Uri): File {

        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "image.jpg")

        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                val buffer = ByteArray(4 * 1024) // Adjust buffer size as needed
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }

        return file
    }

    // date converter
    fun convertStringToDate(inputDate: String?): String? {
        return if (inputDate.isNullOrBlank() || inputDate == "N/S") {
            null
        } else {
            try {
                // Parse the input date string
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
                val date = LocalDate.parse(inputDate, formatter)
                // Format the date in the desired format
                val outputFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy", Locale.ENGLISH)
                date.format(outputFormatter)
            } catch (e: java.time.format.DateTimeParseException) {
                null
            }
        }
    }

    fun getChipColorByStatus(status: String?): Color {
        return when (status) {
            "new" -> green
            "pending" -> blue
            "completed" -> primary
            else -> gray
        }
    }

    suspend fun convertBitmapToFile(context: Context, bitmap: Bitmap?): File {
        return withContext(Dispatchers.IO) {
            val outputStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // Convert bitmap to JPEG format with maximum quality
            val bytes = outputStream.toByteArray()
            val tempFile = File.createTempFile("image", ".jpg", context.cacheDir) // Create a temporary file with .jpg extension
            tempFile.writeBytes(bytes)
            tempFile
        }
    }

    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}