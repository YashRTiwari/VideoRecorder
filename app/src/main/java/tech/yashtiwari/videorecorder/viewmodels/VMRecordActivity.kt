package tech.yashtiwari.videorecorder.viewmodels

import android.util.Log
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

public class VMRecordActivity (val duration : Int) : ViewModel(){

    public var obsDuration : ObservableInt = ObservableInt(duration)

    public fun updateDuration(value : Int) = obsDuration.set(value)

    fun startTimer() {
        viewModelScope.launch {
            delay(5000)
            Log.d(TAG, "startTimer: ")
        }
    }

    companion object {
        private val TAG : String = "VMRecordActivity"
    }
}