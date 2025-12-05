package com.example.mediaplayer

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns.DATE_ADDED
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer.adapter.VideoAdapter
import com.example.mediaplayer.items.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var videoAdapter: VideoAdapter
    private val launcherPermmissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->
        val granted = if (SDK_INT >= TIRAMISU) {
            permisos[READ_MEDIA_VIDEO] == true
        } else {
            permisos[READ_EXTERNAL_STORAGE] == true
        }
        if (granted) loadVideos()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Configurar RecyclerView
        val multimediaReciclerView: RecyclerView = findViewById(R.id.recyclerView)
        multimediaReciclerView.layoutManager = LinearLayoutManager(this)

        // Cargar una lista vacia al recyclerView
        videoAdapter = VideoAdapter(mutableListOf(), this)

        // Mostrar los videos al hacer click en el boton
        findViewById<Button>(R.id.btnVideo).setOnClickListener {
            multimediaReciclerView.adapter = videoAdapter
            requestPermissionsAndLoad()
        }
    }

    private fun requestPermissionsAndLoad() {
        val perms = if (SDK_INT >= TIRAMISU) {
            arrayOf(READ_MEDIA_VIDEO)
        } else {
            arrayOf(READ_EXTERNAL_STORAGE)
        }
        launcherPermmissions.launch(perms)
    }

    // Función para cargar los videos desde el almacenamiento externo
    private fun loadVideos() {
        lifecycleScope.launch(Dispatchers.IO) {
            val videos = mutableListOf<Video>()
            val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            // Consulta para obtener los videos
            val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION
            )
            val sort = "$DATE_ADDED DESC"
            val cursor = contentResolver.query(
                uri,
                projection,
                null,
                null,
                sort
            )
            cursor?.use {
                val idCol = it.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val nameCol = it.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val durationCol = it.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                while (it.moveToNext()) {
                    val id = it.getLong(idCol)
                    val name = it.getString(nameCol)
                    val duration = it.getInt(durationCol)
                    val contentUri = MediaStore.Video.Media.getContentUri("external", id)
                    videos.add(Video(id, name, contentUri, duration))
                }
            }
            launch(Dispatchers.Main) {
                videoAdapter.update(videos)
            }
        }
    }
}