package com.sitaram.foodshare.utils

import android.content.Context
import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.sitaram.foodshare.theme.blue
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.green
import com.sitaram.foodshare.theme.primary
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Utility class for converting different types of data.
 */
object ConverterUtil {

    /**
     * Converts a URI to a File object.
     * @param context The application context.
     * @param uri The URI to convert.
     * @return The converted File object.
     */
    fun convertUriToFile(context: Context, uri: Uri): File {

        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "${uri.toString().substringAfterLast("/")}.png")
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

    /**
     * Converts a string date in "yyyy-MM-dd" format to "dd MMM, yyyy" format.
     * @param inputDate The input date string.
     * @return The formatted date string.
     */
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

    /**
     * Returns a color based on the status.
     * @param status The status string.
     */
    fun getChipColorByStatus(status: String?): Color {
        return when (status) {
            "new" -> green
            "pending" -> blue
            "completed" -> primary
            else -> gray
        }
    }
}