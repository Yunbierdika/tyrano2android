package yunbierdika.tyrano2android.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.webkit.JavascriptInterface
import yunbierdika.tyrano2android.R

class JavaScriptInterface(
    private val activity: Activity,
    writeLogToLocal: WriteLogToLocal
) {
    private val saveFileManager = SaveFileManager(activity, writeLogToLocal)

    // 将发送过来的存档保存到指定目录
    @JavascriptInterface
    fun setStorage(fileName: String, saveData: String) {
        saveFileManager.setStorage("$fileName.sav", saveData)
    }

    // 加载存档，返回值为存档文件里的内容
    @JavascriptInterface
    fun getStorage(fileName: String): String? {
        return saveFileManager.getStorage("$fileName.sav")
    }

    // 使结束游戏按钮功能可用
    @JavascriptInterface
    fun finishGame() {
        // 调用 Activity 的 finish() 方法
        activity.runOnUiThread {
            // 弹出确认框
            AlertDialog.Builder(activity)
                .setTitle(R.string.back_pressed_title)
                .setMessage(R.string.back_pressed_message)
                .setPositiveButton(R.string.back_pressed_positive) { dialog: DialogInterface?, which: Int ->
                    run {
                        activity.finish()
                        activity.startActivity(activity.intent)
                    }
                }
                .setNegativeButton(R.string.back_pressed_negative, null)
                .show()
        }
    }
}