package tech.yashtiwari.videorecorder

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaScannerConnection
import android.net.Uri
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

public enum class MediaType {
    VIDEO, PICTURE
}
data class VideoModel(val name: String,
                      val duration: String,
                      val time: String?,
                      val file : File,
                        val TYPE : MediaType)

object Utility {

    private const val FILENAME = "yyyy_MM_dd_HH_mm_ss"
    private const val VIDEO_EXTENSION = ".mp4"
    public const val IMAGE_EXTENSION = ".jpeg"
    private  const val TAG = "Utility"
    private const val DATE_INBUILT_FORMAT = "yyyyMMdd'T'HHmmss.SSS'Z'"
    private const val DATE_OUT_FORMAT = "yyyy/MM/dd HH:mm"

    fun getOutputDirectory(context: Context): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
    }

    fun createFile(baseFolder: File, fileName: String, extension: String = VIDEO_EXTENSION) =
        File(baseFolder,  fileName+extension)

    fun callScanIntent(context: Context?, path: String) {
        MediaScannerConnection.scanFile(
            context,
            arrayOf(path),
            null
        ) { p, uri -> Log.d(TAG, p) }
    }

    fun fileExists(fileDir: File, name : String) : Boolean {
       return when(fileDir.list()?.contains("$name$VIDEO_EXTENSION")){
            true -> true
            else -> false;
        }
    }


}