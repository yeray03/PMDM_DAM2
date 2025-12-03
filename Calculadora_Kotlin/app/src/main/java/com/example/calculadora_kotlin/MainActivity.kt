package com.example.calculadora_kotlin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import operations.doOperation
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    var reset: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<TextView>(R.id.error).visibility = TextView.INVISIBLE
        findViewById<Button>(R.id.boton0).setOnClickListener { botonPulsado(getString(R.string.boton0)) }
        findViewById<Button>(R.id.boton1).setOnClickListener { botonPulsado(getString(R.string.boton1)) }
        findViewById<Button>(R.id.boton2).setOnClickListener { botonPulsado(getString(R.string.boton2)) }
        findViewById<Button>(R.id.boton3).setOnClickListener { botonPulsado(getString(R.string.boton3)) }
        findViewById<Button>(R.id.boton4).setOnClickListener { botonPulsado(getString(R.string.boton4)) }
        findViewById<Button>(R.id.boton5).setOnClickListener { botonPulsado(getString(R.string.boton5)) }
        findViewById<Button>(R.id.boton6).setOnClickListener { botonPulsado(getString(R.string.boton6)) }
        findViewById<Button>(R.id.boton7).setOnClickListener { botonPulsado(getString(R.string.boton7)) }
        findViewById<Button>(R.id.boton8).setOnClickListener { botonPulsado(getString(R.string.boton8)) }
        findViewById<Button>(R.id.boton9).setOnClickListener { botonPulsado(getString(R.string.boton9)) }
        findViewById<Button>(R.id.botonSuma).setOnClickListener { botonPulsado(getString(R.string.botonSuma)) }
        findViewById<Button>(R.id.botonResta).setOnClickListener { botonPulsado(getString(R.string.botonResta)) }
        findViewById<Button>(R.id.botonIgual).setOnClickListener { botonPulsado(getString(R.string.botonIgual)) }

    }

    fun botonPulsado(boton: String) {
        val resultadoTextView: TextView = findViewById<TextView>(R.id.resultado)
        var operacion: String = resultadoTextView.text.toString()

        if (reset && boton != "=" && boton != "+" && boton != "-") {
            operacion = ""
            resultadoTextView.text = ""
            findViewById<TextView>(R.id.error).visibility = TextView.INVISIBLE;
            reset = false
        }else{
            reset = false
        }

        when (boton) {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-" -> resultadoTextView.text = operacion + boton
            "=" -> {
                try {
                    val resultado = doOperation(operacion)
                    if (resultado != null) {
                        resultadoTextView.text = resultado
                        reset = true

                    } else {
                        findViewById<TextView>(R.id.error).visibility = TextView.VISIBLE;
                        reset = true
                    }
                } catch (e: Exception) {
                    findViewById<TextView>(R.id.error).visibility = TextView.VISIBLE;
                    reset = true
                }
            }

            else -> resultadoTextView.text = "esto no deberia salir"
        }

    }

}