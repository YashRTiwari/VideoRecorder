package tech.yashtiwari.videorecorder.viewmodels

import android.app.Application
import androidx.lifecycle.*
import tech.yashtiwari.videorecorder.Utility
import tech.yashtiwari.videorecorder.VRApplication
import tech.yashtiwari.videorecorder.VideoModel
import tech.yashtiwari.videorecorder.db.Media
import tech.yashtiwari.videorecorder.db.MediaDatabase
import tech.yashtiwari.videorecorder.db.MediaRepository

class VMVideoList(application: Application) : AndroidViewModel(application) {

    val mlMediaList : LiveData<List<Media>>
    private val repository: MediaRepository

    init {
        val mediaDao = MediaDatabase.getDatabase(application, viewModelScope).mediaDao()
        repository = MediaRepository(mediaDao)
        mlMediaList = repository.allMedia
    }

}