package tech.yashtiwari.videorecorder

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaScannerConnection
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import kotlin.collections.ArrayList

data class VideoModel(val name: String, val duration: String, val time: String)

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

    // TODO
    fun getMediaDuration(context: Context, directory: File): ArrayList<VideoModel> {
        val listOfVideos = ArrayList<VideoModel>()
        if (!directory.exists()) return listOfVideos

        directory.listFiles()?.forEach {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(context, Uri.parse(it.absolutePath))
            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val time  = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE)
            listOfVideos.add(VideoModel(it.name, duration, time))
            retriever.release()
        }

        return listOfVideos
    }



}