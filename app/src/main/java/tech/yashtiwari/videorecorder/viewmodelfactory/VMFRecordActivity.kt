package tech.yashtiwari.videorecorder.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.yashtiwari.videorecorder.viewmodels.VMRecordActivity

public  class VMFRecordActivity(val duration : Int) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VMRecordActivity(duration) as T
    }
}