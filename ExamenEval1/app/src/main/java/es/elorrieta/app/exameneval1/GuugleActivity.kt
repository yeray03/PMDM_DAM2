package es.elorrieta.app.exameneval1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.net.toUri

class GuugleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_guugle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Your code here! ---//

        val url = findViewById<TextView>(R.id.editTextURLActivityGuugle)
        val btnCancel = findViewById<Button>(R.id.buttonCancelActivityGuugle)
        val btnBuscar = findViewById<Button>(R.id.btnFind)


        // abrir en navegador
        btnBuscar.setOnClickListener {
            try {
                val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(url.text.toString()))
                startActivity(intentBrowser)
            }catch (e: Exception){
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        //volver a user
        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun extraerVideoId(url: String): String {
        return when {
            url.contains("youtube.com/watch?v=") -> {
                url.substringAfter("watch?v=").substringBefore("&")
            }

            url.contains("youtu.be/") -> {
                url.substringAfter("youtu.be/").substringBefore("?")
            }

            url.contains("youtube.com/embed/") -> {
                url.substringAfter("embed/").substringBefore("?")
            }

            else -> url
        }
    }
}