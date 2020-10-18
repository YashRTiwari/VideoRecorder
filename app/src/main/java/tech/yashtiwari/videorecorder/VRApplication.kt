package tech.yashtiwari.videorecorder

import android.app.Application
import android.content.Context
import java.io.File

class VRApplication : Application() {

    init {
        instance  = this
    }

    companion object{
        private lateinit var instance : VRApplication
        private lateinit var outputDirectory : File
        public fun getInstance() : VRApplication = instance
        public fun getOutputDirectory() : File = Utility.getOutputDirectory(applicationContext())
        public fun applicationContext() : Context = instance.applicationContext
    }

}