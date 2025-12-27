package yunbierdika.tyrano2android.utils

import android.app.Activity
import android.util.Log
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.nio.charset.StandardCharsets

class SaveFileManager(
    activity: Activity,
    private val writeLogToLocal: WriteLogToLocal
) {
    private val tag = "SaveFileManager"

    // 目录：Android/data/包名/file/save
    private val saveDir = File(activity.getExternalFilesDir(null), "save")

    init {
        // 创建存档用文件夹
        if (!saveDir.exists()) saveDir.mkdirs()
    }

    // 保存存档
    fun setStorage(key: String, saveData: String) {
        val savePath = "$saveDir/$key.sav"
        try {
            val file = File(savePath)
            PrintWriter(
                BufferedWriter(
                    OutputStreamWriter(
                        FileOutputStream(file), StandardCharsets.UTF_8
                    )
                )
            ).use { pw ->
                pw.print(saveData)
            }
        } catch (e: IOException) {
            writeLogToLocal.logError("Error writing save file: " + e.message, e)
        }
    }

    // 加载存档
    fun getStorage(key: String): String? {
        val savePath = "$saveDir/$key.sav"
        val resultBuilder = StringBuilder()
        try {
            val file = File(savePath)
            if (!file.exists()) {
                Log.d(tag, "Save file not found: $savePath")
                return ""
            }
            FileInputStream(file).use { fis ->
                InputStreamReader(fis, StandardCharsets.UTF_8).use { isr ->
                    BufferedReader(isr).use { br ->
                        val buffer = CharArray(8192)
                        var read: Int
                        while ((br.read(buffer).also { read = it }) != -1) {
                            resultBuilder.appendRange(buffer, 0, read)
                        }
                    }
                }
            }
        } catch (e: IOException) {
            writeLogToLocal.logError("Error reading save file: " + e.message, e)
        }
        return resultBuilder.toString()
    }
}