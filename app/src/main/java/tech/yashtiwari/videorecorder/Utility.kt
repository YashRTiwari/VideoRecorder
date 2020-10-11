package tech.yashtiwari.videorecorder

import android.content.Context
import android.media.MediaScannerConnection
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


object Utility {

    private const val FILENAME = "yyyy_MM_dd_HH_mm_ss"
    private const val VIDEO_EXTENSION = ".mp4"
    private  const val TAG = "Utility"

    fun getOutputDirectory(context: Context): File {
        val appContext = context.applicationContext
        val mediaDir = appContext.externalMediaDirs.firstOrNull()?.let {
            File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else appContext.filesDir
    }

//    fun createFile(baseFolder: File, format: String = FILENAME, extension: String = VIDEO_EXTENSION) =
//        File(baseFolder, SimpleDateFormat(format, Locale.US)
//            .format(System.currentTimeMillis()) + extension)

    fun createFile(baseFolder: File, fileName: String = FILENAME, extension: String = VIDEO_EXTENSION) =
        File(baseFolder,  fileName+ extension)

    fun callScanIntent(context: Context?, path: String) {
        MediaScannerConnection.scanFile(
            context,
            arrayOf(path),
            null
        ) { p, uri -> Log.d(TAG, p) }
    }



}