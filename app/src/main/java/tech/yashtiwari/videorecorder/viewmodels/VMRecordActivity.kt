package tech.yashtiwari.videorecorder.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel

class VMRecordActivity : ViewModel(){

    var obsDuration : ObservableInt = ObservableInt(0)
    var obsIsRecording: ObservableBoolean = ObservableBoolean(false)
    var obsIsCameraReady : ObservableBoolean = ObservableBoolean(false)

    fun setLeftDuration(time: Int) = obsDuration.set(time)
    fun setIsRecording(value : Boolean) = obsIsRecording.set(value)
    fun setIsCameraReady(value : Boolean) = obsIsCameraReady.set(value)

    companion object {
        private val TAG : String = "VMRecordActivity"
    }
}