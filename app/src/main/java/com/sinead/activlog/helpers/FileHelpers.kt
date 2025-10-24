package com.sinead.activlog.helpers

import android.content.Context
import java.io.*
import timber.log.Timber.i

fun write(context: Context, fileName: String, data: String) {

    val file = File(fileName)
    try {
        val file = File(context.filesDir, fileName)
        val outputStreamWriter = OutputStreamWriter(FileOutputStream(file))
        outputStreamWriter.write(data)
        outputStreamWriter.close()
    } catch (e: Exception) {
        i("Cannot read file: " + e.toString())
    }
}

fun read(context: Context, fileName: String): String {
    val file = File(context.filesDir, fileName)
    var str = ""
    try {
        val inputStreamReader = InputStreamReader(FileInputStream(file))
        if (inputStreamReader != null) {
            val bufferedReader = BufferedReader(inputStreamReader)
            val partialStr = StringBuilder()
            var done = false
            while (!done) {
                val line = bufferedReader.readLine()
                done = (line == null);
                if (line != null) partialStr.append(line);
            }
            inputStreamReader.close()
            str = partialStr.toString()
        }
    } catch (e: FileNotFoundException) {
        i("Cannot Find file: $e")
    } catch (e: IOException) {
        i("Cannot Read file: $e")
    }
    return str
}

fun exists(context: Context, fileName: String): Boolean {
    val file = File(context.filesDir,fileName)
    return file.exists()
}
