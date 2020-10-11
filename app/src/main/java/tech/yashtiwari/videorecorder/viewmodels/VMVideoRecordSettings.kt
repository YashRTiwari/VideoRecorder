package tech.yashtiwari.videorecorder.viewmodels

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable

class VMVideoRecordSettings : ViewModel() {
    public var obsDuration : ObservableInt = ObservableInt(15)
    public var obsVideoName : ObservableField<String> = ObservableField("")
}