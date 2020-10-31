package tech.yashtiwari.videorecorder

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaScannerConnection
import android.net.Uri
import android.util.Log
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

enum class Media {
    VIDEO, PICTURE
}
data class VideoModel(val name: String,
                      val duration: String,
                      val time: String?,
                      val file : File,
                        val TYPE : Media)

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

    fun getVideoList(): List<VideoModel> {
        val listOfVideos = ArrayList<VideoModel>()
        val context = VRApplication.applicationContext()
        val directory = VRApplication.getOutputDirectory()
        if (!directory.exists()) return listOfVideos

        directory.listFiles()?.forEach {
            var file : VideoModel? = convertFileToVideoModelObject(context, it)
            file?.let { listOfVideos.add(file) }
        }

        var sortedList = listOfVideos.sortedWith(compareBy({ it.time }))

        return sortedList
    }

    fun convertFileToVideoModelObject(context: Context, file : File) : VideoModel? {
        return file.let {

            if(file.name.contains(IMAGE_EXTENSION)){
                VideoModel(it.name, "", "", it, Media.PICTURE)
            } else if(file.name.contains(VIDEO_EXTENSION)) {
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(context, Uri.parse(it.absolutePath))
                val duration = getTimeInMinSeconds(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))
                val time: Date? =
                    getReadableDate(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE))
                val stringTime =
                    time?.let { SimpleDateFormat(DATE_OUT_FORMAT, Locale.getDefault()).format(it) }
                retriever.release()

                VideoModel(it.name, duration, stringTime, it, Media.VIDEO)
            } else {
                return null
            }
        }
    }

    private fun getTimeInMinSeconds(value: String) : String {
        val iValue = value.toLong()
        val minutes = TimeUnit.MILLISECONDS.toMinutes(iValue)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(iValue - (minutes * 60))
        return "$minutes : $seconds"
    }

    private fun getReadableDate(value: String) : Date? {
        val formatter = SimpleDateFormat(DATE_INBUILT_FORMAT, Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return try {
            formatter.parse(value)
        } catch (e: Exception) {
            null
        }
    }


}