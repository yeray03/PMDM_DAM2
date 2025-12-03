package com.example.intentsexplicitos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProvinciaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_provincia)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        findViewById<Button>(R.id.select2).setOnClickListener {
            val select = findViewById<RadioGroup>(R.id.group).checkedRadioButtonId
            if (select != -1) {
                val prov = findViewById<RadioButton>(select).text.toString()
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.putExtra("provincia", prov)
                startActivity(intent)
            } else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }
        }


    }

}