package com.example.mediaplayer.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mediaplayer.R
import androidx.core.net.toUri


class AudioPlayer : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var seekBar: SeekBar
    private lateinit var btnPlayPause: Button
    private lateinit var tvCurrentTime: TextView
    private lateinit var tvTotalTime: TextView

    private lateinit var tvAudioTitle: TextView
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audio_player)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Inicializar vistas
        seekBar = findViewById(R.id.seekBar)
        btnPlayPause = findViewById(R.id.btnPlayPause)
        tvCurrentTime = findViewById(R.id.tvCurrentTime)
        tvTotalTime = findViewById(R.id.tvTotalTime)
        tvAudioTitle = findViewById(R.id.tvAudioTitle)

        val audioUri = intent.getStringExtra("audioUri")
        val name = intent.getStringExtra("audioNombre")

        tvAudioTitle.text = getString(R.string.playing_audio, name)



        audioUri?.let {
            // Configurar MediaPlayer
            mediaPlayer = MediaPlayer().apply {
                setDataSource(applicationContext, it.toUri())
                prepare()
                start()
            }

            // Configurar SeekBar y duración total
            seekBar.max = mediaPlayer?.duration ?: 0
            tvTotalTime.text = formatTime(mediaPlayer?.duration ?: 0)

            // Iniciar actualización de SeekBar
            updateSeekBar()
        }

        // Botón Play/Pause
        btnPlayPause.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                btnPlayPause.text = getString(R.string.play)
            } else {
                mediaPlayer?.start()
                btnPlayPause.text = getString(R.string.pause)
            }
        }

        // SeekBar
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateSeekBar() {
        mediaPlayer?.let {
            seekBar.progress = it.currentPosition
            tvCurrentTime.text = formatTime(it.currentPosition)
        }
        handler.postDelayed({ updateSeekBar() }, 1000)
    }

    private fun formatTime(millis: Int): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        mediaPlayer?.release()
        mediaPlayer = null
    }
}