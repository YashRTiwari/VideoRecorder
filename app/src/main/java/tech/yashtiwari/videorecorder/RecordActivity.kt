package tech.yashtiwari.videorecorder

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.VideoCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_record.*
import tech.yashtiwari.videorecorder.viewmodelfactory.VMFRecordActivity
import tech.yashtiwari.videorecorder.viewmodels.VMRecordActivity
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// https://stackoverflow.com/questions/56054647/can-i-record-video-with-camerax-android-jetpack/56055930#56055930
// https://placona.co.uk/building-a-video-recording-application-in-android-with-camerax/
// https://www.tutorialspoint.com/how-to-get-full-screen-activity-in-android
// https://stackoverflow.com/questions/20001662/how-to-update-gallery-after-moving-photo-programmatically


class RecordActivity : AppCompatActivity(), LifecycleOwner {

    private  var videoCapture : VideoCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private var fileName : String? = null
    private var duration : Int = 0
    private var currentCameraSelected : CameraSelector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar?.hide();


        // Set up the listener for take photo button
        ibVideoCapture.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) startRecording()
            else stopRecording()
        }
        ibRotate.setOnClickListener { flipCamera() }

        intent?.apply {
            fileName = this.getStringExtra("name")
            duration = this.getIntExtra("duration", 0)
        }

        outputDirectory = Utility.getOutputDirectory(this)
        cameraExecutor = Executors.newSingleThreadExecutor()

    }

    private fun flipCamera() {
        if (currentCameraSelected == CameraSelector.DEFAULT_BACK_CAMERA)
            startCamera(CameraSelector.DEFAULT_FRONT_CAMERA)
        else
            startCamera(CameraSelector.DEFAULT_BACK_CAMERA)
    }

    override fun onResume() {
        super.onResume()
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }


    @SuppressLint("RestrictedApi")
    private fun stopRecording() {
        videoCapture?.stopRecording()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun startRecording() {

        fileName?.apply {
            val file = Utility.createFile(outputDirectory, this)
            videoCapture?.startRecording(file, Executors.newSingleThreadExecutor(), object : VideoCapture.OnVideoSavedCallback{
                override fun onVideoSaved(file: File) {
                    Log.i(TAG, "Video File : $file")
                    Utility.callScanIntent(this@RecordActivity, file.path)
                }
                override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
                    Log.i(TAG, "Video Error: $message")
                }
            })
        }
    }

    @SuppressLint("RestrictedApi")
    private fun startCamera(cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA) {

        currentCameraSelected = cameraSelector

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            videoCapture = VideoCapture.Builder().build()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.createSurfaceProvider())
                }

            // Select back camera as a default
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, videoCapture)
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))

    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onDestroy() {
        cameraExecutor.shutdown()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO)
    }



}