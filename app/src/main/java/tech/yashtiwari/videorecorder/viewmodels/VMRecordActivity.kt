package tech.yashtiwari.videorecorder.viewmodels

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.yashtiwari.videorecorder.MediaType
import tech.yashtiwari.videorecorder.db.Media
import tech.yashtiwari.videorecorder.db.MediaDatabase
import tech.yashtiwari.videorecorder.db.MediaRepository

class VMRecordActivity(application: Application) : AndroidViewModel(application){

    var obsDuration : ObservableInt = ObservableInt(0)
    var obsIsRecording: ObservableBoolean = ObservableBoolean(false)
    var obsIsCameraReady : ObservableBoolean = ObservableBoolean(false)
    private val repository: MediaRepository

    fun setLeftDuration(time: Int) = obsDuration.set(time)
    fun setIsRecording(value : Boolean) = obsIsRecording.set(value)
    fun setIsCameraReady(value : Boolean) = obsIsCameraReady.set(value)

    fun addMediaToDB(path: String, name: String, duration: Int, type: MediaType) {
         val media: Media = Media(0, name = name, path = path, duration = duration, type = type.name)
         viewModelScope.launch{
            repository.insert(media)
        }
    }


    init{
        val mediaDao = MediaDatabase.getDatabase(application, viewModelScope).mediaDao()
        repository = MediaRepository(mediaDao)
    }

    companion object {
        private val TAG : String = "VMRecordActivity"
    }
}