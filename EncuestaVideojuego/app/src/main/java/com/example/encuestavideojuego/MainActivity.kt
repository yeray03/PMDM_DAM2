package com.example.encuestavideojuego

import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        findViewById<RadioButton>(R.id.fallout).setOnClickListener { findViewById<TextView>(R.id.cualEscoges).text = getString(R.string.hasEscogido, getString(R.string.opcion1)) }
        findViewById<RadioButton>(R.id.lol).setOnClickListener { findViewById<TextView>(R.id.cualEscoges).text = getString(R.string.hasEscogido, getString(R.string.opcion2)) }
        findViewById<RadioButton>(R.id.fortnite).setOnClickListener { findViewById<TextView>(R.id.cualEscoges).text = getString(R.string.hasEscogido, getString(R.string.opcion3)) }
        findViewById<RadioButton>(R.id.tf2).setOnClickListener { findViewById<TextView>(R.id.cualEscoges).text = getString(R.string.hasEscogido, getString(R.string.opcion4)) }

        findViewById<CheckBox>(R.id.checkBox).setOnClickListener {
            if (findViewById<CheckBox>(R.id.checkBox).isChecked){
                findViewById<CheckBox>(R.id.checkBox).text = getString(R.string.si)
                }
            else  {
                findViewById<CheckBox>(R.id.checkBox).text = getString(R.string.no)
            }

        }
    }

}