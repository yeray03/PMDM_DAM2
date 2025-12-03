package com.example.llamadawwanonima

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

var llamando : Boolean = false
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val telefonoBtn = findViewById<ImageButton>(R.id.telefono)
        val imagen = findViewById<ImageView>(R.id.imageView)
        val textView = findViewById<TextView>(R.id.textview)


        // clase anonima
        telefonoBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!llamando) {
                    telefonoBtn.setImageResource(R.drawable.colgar)
                    textView.text = getString(R.string.llamando)
                    imagen.setImageResource(R.drawable.walterwhite2)
                    llamando = true
                } else {
                    telefonoBtn.setImageResource(R.drawable.llamar)
                    textView.text = getString(R.string.fin)
                    imagen.setImageResource(R.drawable.walterwhite)
                    llamando = false
                }
            }
        })

        //clase Lambda

          //  if (!llamando) {
             //   findViewById<ImageButton>(R.id.telefono).setImageResource(R.drawable.colgar)
            //    findViewById<TextView>(R.id.textview).text = getString(R.string.llamando)
            //    findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.walterwhite2)
            //    llamando = true
            //} else {
             //   findViewById<ImageButton>(R.id.telefono).setImageResource(R.drawable.llamar)
             //   findViewById<TextView>(R.id.textview).text = getString(R.string.fin)
              //  findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.walterwhite)
              //  llamando = false
           // }
        //}
    }
}