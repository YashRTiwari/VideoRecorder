package tech.yashtiwari.videorecorder.activity

import android.widget.MediaController
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_player.*
import tech.yashtiwari.videorecorder.R
import tech.yashtiwari.videorecorder.db.Media

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        supportActionBar?.hide();

        val controller = MediaController(this)
        controller.setMediaPlayer(videoView)
        videoView.setMediaController(controller)

        intent?.getParcelableExtra<Media>("media")?.let {
            handleMedia(it)
        } ?: Toast.makeText(this, "Nope", Toast.LENGTH_SHORT).show()

    }

    private fun handleMedia(media: Media) {

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun releasePlayer() {
        videoView.stopPlayback()
    }

    private fun runVideo(path : String){
        videoView.setVideoPath(path)
        videoView.start()
    }
}