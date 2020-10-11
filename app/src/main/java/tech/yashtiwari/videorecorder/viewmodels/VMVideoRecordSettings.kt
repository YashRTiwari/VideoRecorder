package tech.yashtiwari.videorecorder.viewmodels

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fragment_video_record_settings.*
import tech.yashtiwari.videorecorder.Utility
import java.io.File

class VMVideoRecordSettings : ViewModel() {
    public var obsDuration : ObservableInt = ObservableInt(15)
    public var obsVideoName : ObservableField<String> = ObservableField("")
    public var obsIsFileExists = ObservableBoolean(false)

    fun updateName(s: String) = obsVideoName.set(s).also { obsIsFileExists.set(false) }

    fun updateDuration(i : Int) = obsDuration.set(i)

    fun IsFilevalid(outputDirectory : File, s: String): Boolean  {
        obsIsFileExists.set(Utility.fileExists(outputDirectory, s))
        return !obsIsFileExists.get();
    }

    fun updateDurationValue(value: Float) = obsDuration.set(value.toInt())

}