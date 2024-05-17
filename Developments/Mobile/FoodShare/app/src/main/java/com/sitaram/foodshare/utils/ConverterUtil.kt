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
import kotlin.math.log10
import kotlin.math.pow

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

fun convertUriToFile(context: Context?, uri: Uri): File? {
    val inputStream = context?.contentResolver?.openInputStream(uri)
    val mimeType = context?.contentResolver?.getType(uri)
    var file: File? = null
    if (inputStream != null && mimeType != null) {
        val extension = when (mimeType) {
            "image/jpeg" -> "jpg"
            "image/png" -> "png"
            "image/gif" -> "gif"
            "image/bmp" -> "bmp"
            "application/pdf" -> "pdf"
            "text/plain" -> "txt"
            "text/csv" -> "csv"
            "application/json" -> "json"
            "application/xml" -> "xml"
            "application/msword" -> "doc"
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> "docx"
            "application/vnd.ms-excel" -> "xls"
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" -> "xlsx"
            else -> {
                val lastSlash = mimeType.lastIndexOf('/')
                if (lastSlash != -1) {
                    mimeType.substring(lastSlash + 1)
                } else {
                    "file"
                }
            }
        }
        val fileName = getFileName(context, uri) ?: "file.$extension"
        file = File(context.cacheDir, "$fileName.$extension")

        inputStream.use { input ->
            FileOutputStream(file).use { output ->
                val buffer = ByteArray(4 * 1024) // Adjust buffer size as needed
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }
    }
    return file
}

fun getFileName(context: Context?, uri: Uri): String? {
    var fileName: String? = null
    context?.contentResolver?.query(uri, null, null, null, null)?.use { cursor ->
        val displayNameIndex = cursor.getColumnIndex("_display_name")
        if (displayNameIndex >= 0 && cursor.moveToFirst()) {
            fileName = cursor.getString(displayNameIndex)
        }
    }
    return fileName?.substringBeforeLast(".")
}

fun formatFileSize(size: Long): String {
    if (size <= 0) return "0 B"
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
    return String.format("%.1f %s", size / 1024.0.pow(digitGroups.toDouble()), units[digitGroups])
}