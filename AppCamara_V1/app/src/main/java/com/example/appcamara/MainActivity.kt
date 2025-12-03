package com.example.appcamara

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1

        private const val REQUEST_IMAGE_SAVE = 2

        private const val REQUEST_VIDEO_CAPTURE = 3
        private const val CAMERA_PERMISSION_CODE = 100
    }

    private var currentPhotoUri: Uri? = null
    private var currentVideoUri: Uri? = null

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
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                camaraThumbnail()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }
        btnFotoArchivo.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                camaraArchivo()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }

        btnVideo.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                camaraVideo()
            }
        }
        videoView.setOnClickListener {
            if (videoView.isPlaying){
                videoView.pause()
            }else{
                videoView.start()
            }
        }
    }

    private fun camaraThumbnail() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
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
            val contentValues = ContentValues().apply { // Metadatos de la imagen
                put(MediaStore.Images.Media.DISPLAY_NAME, "${System.currentTimeMillis()}.jpg") // Nombre del archivo
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg") // Tipo de archivo
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES) // Carpeta de destino
            }

            currentPhotoUri = contentResolver.insert(  // Insertamos la imagen en el MediaStore
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) // Intent para capturar la imagen
            intent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri) // URI donde se guardará la imagen
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION) // Permiso de escritura
            startActivityForResult(intent, REQUEST_IMAGE_SAVE) // Iniciamos la actividad
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
            val contentValues = ContentValues().apply { // Metadatos del video
                put(MediaStore.Video.Media.DISPLAY_NAME, "${System.currentTimeMillis()}.mp4") // Nombre del archivo
                put(MediaStore.Video.Media.MIME_TYPE, "video/mp4") // Tipo de archivo
                put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM) // Carpeta de destino
            }

            currentVideoUri = contentResolver.insert( // Insertamos el video en el MediaStore
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE) // Intent para capturar el video
            intent.putExtra(MediaStore.EXTRA_OUTPUT, currentVideoUri) // URI donde se guardará el video
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5) // Duración máxima en segundos
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION) // Permiso de escritura
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE) // Iniciamos la actividad
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "Error: no se puede abrir la cámara",
                Toast.LENGTH_LONG
            ).show()
            e.stackTrace

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {

        // Respuesta del recurso que ha aceptado la acción
        super.onActivityResult(requestCode, resultCode, intent)

        // Tenemos respuesta correcta...
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            // Obtenemos la miniatura de la foto
            val extras = intent?.extras
            val imageBitmap = extras?.get("data") as Bitmap

            // La mostramos en un ImageView
            val imageView = findViewById<ImageView>(R.id.thumbnail)
            imageView.setImageBitmap(imageBitmap)
        } else if (requestCode == REQUEST_IMAGE_SAVE && resultCode == RESULT_OK) {
            // La imagen se ha guardado correctamente
            val uri = currentPhotoUri
            if (uri != null) {
                Toast.makeText(
                    applicationContext,
                    "Imagen guardada en: $uri",
                    Toast.LENGTH_LONG
                ).show()
            }

        } else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            val uri = currentVideoUri // Obtenemos el URI del video guardado
            if (uri != null) {
                val videoView = findViewById<android.widget.VideoView>(R.id.videoView)
                videoView.visibility = View.VISIBLE // Hacemos visible el VideoView
                videoView.setVideoURI(uri) // Asignamos el URI al VideoView
                videoView.start() // Iniciamos la reproducción del video

                Toast.makeText(
                    applicationContext,
                    "Video guardado en: $uri",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            // Algo ha ido mal...
            Toast.makeText(
                applicationContext,
                "error: no se ha podido capturar la imagen",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
