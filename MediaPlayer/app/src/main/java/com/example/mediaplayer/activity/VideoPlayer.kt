package com.example.mediaplayer.activity

import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mediaplayer.R
import androidx.core.net.toUri

class VideoPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_video_player)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val videoUri = intent.getStringExtra("videoUri")
        val videoView = findViewById<VideoView>(R.id.videoView)

        // Configurar y reproducir el video
        videoUri?.let {
            videoView.setVideoURI(it.toUri())
            videoView.start()
        }
        val mediacontroller = MediaController(this)
//        mediacontroller.setAnchorView(videoView)
        videoView.setMediaController(mediacontroller)

    }
}