package com.example.numeroprimo_kotlin

import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import operations.isPrimeNumber

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
        val boton: Button = findViewById(R.id.button)
        val textoSuperior: TextView = findViewById(R.id.textView)
        val numeros: EditText = findViewById(R.id.editTextNumber2)

        boton.setOnClickListener {
            //texto actual boton
            val botonCurrent = boton.getText().toString()

            // si el texto del boton es "comprobar"
            if (botonCurrent == getString(R.string.boton_comprobar) // el texto del editText no es el de "introduce tu número primo"
                && (numeros.getText()
                    .toString() != getString(R.string.IntroNum)) && !numeros.getText().toString()
                    .trim { it <= ' ' }.isEmpty()
            ) {
                boton.setText(R.string.boton_reiniciar)
                numeros.visibility = View.INVISIBLE
                try {
                    val numero = numeros.getText().toString().toInt()
                    if (isPrimeNumber(numero)) {
                        val esPrimo =
                            getString(R.string.elNumero) + numero + getString(R.string.Primo)
                        textoSuperior.text = esPrimo
                    } else {
                        val noPrimo =
                            getString(R.string.elNumero) + numero + getString(R.string.NoPrimo)
                        textoSuperior.text = noPrimo
                    }
                } catch (e: NumberFormatException) {
                    textoSuperior.setText(R.string.NoPrimo)
                    boton.setText(R.string.boton_reiniciar)
                    numeros.visibility = View.INVISIBLE
                    e.printStackTrace()
                }
            } else {
                boton.setText(R.string.boton_comprobar)
                numeros.visibility = View.VISIBLE
                // restablece el limite de caracteres para ver el placeholder
                numeros.setFilters(arrayOf<InputFilter>(LengthFilter(30)))
                numeros.setText(getString(R.string.IntroNum))
                textoSuperior.setText(R.string.textView)
                numeros.clearFocus()
            }
        }

        numeros.onFocusChangeListener = View.OnFocusChangeListener{ v, hasFocus ->
            if (hasFocus) {
                numeros.setText("")
                // quita el limite de caracteres para que el usuario pueda escribir su número
                numeros.setFilters(arrayOf<InputFilter>(LengthFilter(4)))
            } else {
                if (numeros.getText().toString().trim { it <= ' ' }.isEmpty()) {
                    numeros.setText(R.string.IntroNum)
                    // restablece el limite de caracteres para ver el placeholder
                    numeros.setFilters(arrayOf<InputFilter>(LengthFilter(30)))
                }
            }
        }
    }
}