package com.example.appcamara_actualizada

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Constantes para los códigos de solicitud
    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
    }

    // Variables para almacenar las URIs actuales de foto y video
    private var currentPhotoUri: Uri? = null
    private var currentVideoUri: Uri? = null

    // Launcher para capturar foto del thumbnail
    private val sacarFotoThumbnail = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            findViewById<ImageView>(R.id.thumbnail).setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "Error: no se ha podido capturar la imagen", Toast.LENGTH_LONG).show()
        }
    }

    // Launcher para guardar foto en galeria
    private val sacarFotoGaleria = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            currentPhotoUri?.let {
                Toast.makeText(this, "Imagen guardada en: $it", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Error: no se ha podido capturar la imagen", Toast.LENGTH_LONG).show()
        }
    }

    // Launcher para capturar video, guardarlo en galeria y reproducirlo en VideoView
    private val grabarVideo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultCode ->
        if (resultCode.resultCode == RESULT_OK) {
            currentVideoUri?.let { uri ->
                Toast.makeText(this, "Video guardado en: $uri", Toast.LENGTH_LONG).show()
                findViewById<android.widget.VideoView>(R.id.videoView).apply {
                    visibility = View.VISIBLE
                    setVideoURI(uri)
                    start()
                }
            }
        } else {
            Toast.makeText(this, "Error: no se ha podido capturar el video", Toast.LENGTH_LONG).show()
        }
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
        val btnFotoThumbnail = findViewById<android.widget.Button>(R.id.btnFotoThumbnail)
        val btnFotoArchivo = findViewById<android.widget.Button>(R.id.btnFotoArchivo)
        val btnVideo = findViewById<android.widget.Button>(R.id.btnVideo)
        val videoView = findViewById<android.widget.VideoView>(R.id.videoView)
        videoView.visibility = View.INVISIBLE

        btnFotoThumbnail.setOnClickListener {
            // Verificamos el permiso de cámara
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                camaraThumbnail() // Abrimos la cámara para capturar el thumbnail
            } else { // Solicitamos el permiso de cámara
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }
        btnFotoArchivo.setOnClickListener {
            // Verificamos el permiso de cámara
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                camaraArchivo()
            } else { // Solicitamos el permiso de cámara
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }

        btnVideo.setOnClickListener {
            // Verificamos el permiso de cámara
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                camaraVideo()
            }else { // Solicitamos el permiso de cámara
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }
        videoView.setOnClickListener {
            // Pausamos o reanudamos la reproducción del video al hacer clic en él
            if (videoView.isPlaying){
                videoView.pause()
            }else{
                videoView.start()
            }
        }
    }

    private fun camaraThumbnail() {
        try {
            sacarFotoThumbnail.launch(null)
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "Error: no se puede abrir la cámara",
                Toast.LENGTH_LONG
            ).show()
            e.stackTrace
        }
    }

    private fun camaraArchivo() {
        try {
            // Configuramos los metadatos de la imagen
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "${System.currentTimeMillis()}.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            // Insertamos la nueva imagen en el MediaStore
            currentPhotoUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            currentPhotoUri?.let { sacarFotoGaleria.launch(it) }
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "Error: no se puede abrir la cámara",
                Toast.LENGTH_LONG
            ).show()
            e.stackTrace
        }
    }

    private fun camaraVideo() {
        try {
            val contentValues = ContentValues().apply {
                put(MediaStore.Video.Media.DISPLAY_NAME, "${System.currentTimeMillis()}.mp4")
                put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            }
            // Insertamos el nuevo video en el MediaStore
            currentVideoUri = contentResolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            // Lanzamos el intent para grabar video
            currentVideoUri?.let { uri ->
                val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE).apply {
                    putExtra(MediaStore.EXTRA_OUTPUT, uri)
                    putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5) // 5 segundos
                }
                grabarVideo.launch(intent)
            }
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "Error: no se puede abrir la cámara",
                Toast.LENGTH_LONG
            ).show()
            e.stackTrace
        }
    }
}