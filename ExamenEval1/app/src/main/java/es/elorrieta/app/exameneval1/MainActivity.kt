package es.elorrieta.app.exameneval1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import es.elorrieta.app.exameneval1.adapter.SpinnerAdapter
import es.elorrieta.app.exameneval1.room.RoomDB
import es.elorrieta.app.exameneval1.room.entitys.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

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

        // --- Your code here! ---//
        var usuarios  = listOf<Usuario>()
        // --EJERCICIO 1
        val db = RoomDB(this)
        lifecycleScope.launch (Dispatchers.IO){
             usuarios = db.getUsuarioDao().getAllUsers()
            if (usuarios.isEmpty()){
                db.getUsuarioDao().insertUser(
                    Usuario(
                        id = 0,
                        login = "user",
                        pass = "user",
                        nombre = "Juan",
                        empresa = "MEGASOFT"
                    ),
                    Usuario(
                        id = 0,
                        login = "admin",
                        pass = "admin",
                        nombre = "Ana",
                        empresa = "ELORRIETA"
                    )
                )
            }
            usuarios = db.getUsuarioDao().getAllUsers()
            Log.i("USUARIOS", "Usuarios: ")
            usuarios.forEach { Log.i("USUARIO", "$it") }
        }

        //-------

        //EJERCICIO2
        val login = findViewById<TextView>(R.id.editTextLoginActivityMain)
        val passwd = findViewById<TextView>(R.id.editTextPassActivityMain)
        val btnLogin = findViewById<Button>(R.id.buttonLoginActivityMain)
        var existe = false
        btnLogin.setOnClickListener {
            lifecycleScope.launch (Dispatchers.IO){
                usuarios = db.getUsuarioDao().getAllUsers()
//            Log.i("USUARIOS", "Usuarios: ")
//            usuarios.forEach { Log.i("USUARIO", "$it") }
            }

            usuarios.forEach {
                if(it.login == login.text.toString() && it.pass == passwd.text.toString()){
                    existe = true
                    val intent = Intent(applicationContext, UserActivity::class.java)
                    intent.putExtra("usuario",it)
                    startActivity(intent)
                    finish()
                }
            }
            if (!existe)
            Toast.makeText(applicationContext, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }


    }
}