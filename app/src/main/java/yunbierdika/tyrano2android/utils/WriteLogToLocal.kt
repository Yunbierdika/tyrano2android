package yunbierdika.tyrano2android.utils

import android.app.Activity
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.io.use
import kotlin.let

class WriteLogToLocal(
    private val activity: Activity
) {
    private val tag: String = "WebView"

    // 输出无跟踪错误日志
    fun logError(message: String) { logError(message, null) }

    // 输出有跟踪错误日志
    fun logError(message: String, e: Throwable?) {
        Log.e(tag, message, e)
        writeToLogFile("error", message, e)
    }

    // 输出信息日志
    fun logInfo(message: String) {
        Log.i(tag, message)
        writeToLogFile("info", message, null)
    }

    // 输出调试日志
    fun logDebug(message: String) {
        Log.d(tag, message)
        writeToLogFile("debug", message, null)
    }

    // 输出警告日志
    fun logWarn(message: String) {
        Log.w(tag, message)
        writeToLogFile("warn", message, null)
    }

    // 将错误日志保存到指定目录
    fun writeToLogFile(type: String, message: String, e: Throwable?) {
        // 日志目录：/Android/data/包名/file
        val logDir = activity.getExternalFilesDir(null)
        if (logDir == null) {
            Log.e(tag, "无法访问外部存储目录")
            return
        }

        // 错误日志文件
        val logFile = File(logDir, "log.txt")

        try {
            // 追加写入模式
            FileOutputStream(logFile, true).use { fos ->
                OutputStreamWriter(fos, StandardCharsets.UTF_8).use { osw ->
                    BufferedWriter(osw).use { writer ->
                        // 构建日志条目
                        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())

                        val logEntry = when (type) {
                            "info" -> "[$timestamp] INFO: $message"
                            "debug" -> "[$timestamp] DEBUG: $message"
                            "warn" -> "[$timestamp] WARN: $message"
                            else -> "[$timestamp] ERROR: $message"
                        }

                        writer.write(logEntry)
                        writer.newLine()

                        e?.let {
                            writer.write(Log.getStackTraceString(e))
                            writer.newLine()
                        }
                    }
                }
            }
        } catch (ex: IOException) {
            Log.e(tag, "写入日志文件失败", ex)
        }
    }
}