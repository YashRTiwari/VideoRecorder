package tech.yashtiwari.videorecorder

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.VideoCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import kotlinx.android.synthetic.main.activity_record.*
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// https://stackoverflow.com/questions/56054647/can-i-record-video-with-camerax-android-jetpack/56055930#56055930
// https://placona.co.uk/building-a-video-recording-application-in-android-with-camerax/
// https://www.tutorialspoint.com/how-to-get-full-screen-activity-in-android
// https://stackoverflow.com/questions/20001662/how-to-update-gallery-after-moving-photo-programmatically


class RecordActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var videoCapture : VideoCapture
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private var fileName : String = ""
    private var duration : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar?.hide();

        // Set up the listener for take photo button
        btnRecord.setOnClickListener { startRecording() }

        val bundle = intent?.extras
        bundle?.apply {
            fileName = bundle["name"] as String
            duration = bundle["duration"] as Int
        }

        outputDirectory = Utility.getOutputDirectory(this)
        cameraExecutor = Executors.newSingleThreadExecutor()

        btnRecord.setOnClickListener{ startRecording() }
        btnPause.setOnClickListener { stopRecording()}
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
        videoCapture.stopRecording()
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
        val file = Utility.createFile(outputDirectory)
        videoCapture.startRecording(file, Executors.newSingleThreadExecutor(), object : VideoCapture.OnVideoSavedCallback{
            override fun onVideoSaved(file: File) {
                Log.i(TAG, "Video File : $file")
                Utility.callScanIntent(this@RecordActivity, file.path)
            }
            override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
                Log.i(TAG, "Video Error: $message")
            }
        })
    }

    @SuppressLint("RestrictedApi")
    private fun startCamera(cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            videoCapture = VideoCapture.Builder().apply {
                setTargetRotation(viewFinder.display.rotation)
            }.build()

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

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }



}