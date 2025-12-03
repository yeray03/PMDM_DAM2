package com.example.contenedoresprogramaticos

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random


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
        val lay = findViewById<GridLayout>(R.id.grid)
        val botones = arrayListOf<Button>()

        for (i in 0 until 17) {
            val boton = Button(this)
            boton.setOnClickListener { boton.setBackgroundColor(Color.rgb(255,255,255)) }
            randomColor(boton)
            val params = GridLayout.LayoutParams(
                GridLayout.spec(i / 3, 1f),
                GridLayout.spec(i % 3, 1f)
            )
            boton.layoutParams = params
            lay.addView(boton)
            botones.add(boton)
        }

// Botón reset en la tercera columna de la sexta fila
        val botonReset = Button(this)
        botonReset.text = "reset"
        botonReset.setBackgroundColor(Color.rgb(255, 255, 255))
        val paramsReset = GridLayout.LayoutParams(
            GridLayout.spec(5 , 1f),
            GridLayout.spec(2, 1f)
        )
        botonReset.layoutParams = paramsReset
        botonReset.setOnClickListener { botones.forEach { randomColor(it) } } // it representa cada boton del foreach
        lay.addView(botonReset)
    }

}

fun randomColor(boton: Button) {
    boton.setBackgroundColor(
        Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
    )
}




