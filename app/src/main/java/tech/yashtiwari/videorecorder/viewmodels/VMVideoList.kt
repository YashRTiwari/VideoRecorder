package tech.yashtiwari.videorecorder.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.yashtiwari.videorecorder.Utility
import tech.yashtiwari.videorecorder.VRApplication
import tech.yashtiwari.videorecorder.VideoModel

class VMVideoList : ViewModel(){

    var mlVideoList : MutableLiveData<List<VideoModel>> = MutableLiveData()

    init {
        mlVideoList.value = Utility.getVideoList()
    }

    fun checkIfNewFileIsAdded() {
        val newFiles = Utility.getVideoList()
        val currentSize = mlVideoList.value?.size ?: 0
        if (currentSize < newFiles.size){
            mlVideoList.value = newFiles
        }
    }

}