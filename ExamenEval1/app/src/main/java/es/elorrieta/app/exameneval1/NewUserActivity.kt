package es.elorrieta.app.exameneval1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import es.elorrieta.app.exameneval1.adapter.UserAdapter
import es.elorrieta.app.exameneval1.room.RoomDB
import es.elorrieta.app.exameneval1.room.entitys.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Your code here! ---//

        val login = findViewById<TextView>(R.id.editTextLoginActivityNewUser)
        val pass = findViewById<TextView>(R.id.editTextPassActivityNewUser)
        val name = findViewById<TextView>(R.id.editTextNameActivityNewUser)
        val empresa = findViewById<TextView>(R.id.editTextEnterpriseActivityNewUser)
        val btnNuevo = findViewById<Button>(R.id.buttonNewActivityNewUser)
        val btnCancel = findViewById<Button>(R.id.buttonCancelActivityNewUser)


        btnNuevo.setOnClickListener {

            val db = RoomDB(this)

            // Lanzamos esta parte como una Coroutine. Esto significa, un hilo paralelo a la Activity.
            // Más o menos. Si lo hacemos así, podemos luego actualizar la UI fácilmente, y este hilo
            // muere siempre que la Activity también muere
            lifecycleScope.launch(Dispatchers.IO) {

                // Añadimos uno nuevo...
                db.getUsuarioDao().insertUser(
                    Usuario(
                        id = 0,
                        login = login.text.toString(),
                        pass = pass.text.toString(),
                        nombre = name.text.toString(),
                        empresa = empresa.text.toString()
                    )
                )
            }
            Toast.makeText(applicationContext, "Usuario añadido a la BBDD", Toast.LENGTH_SHORT).show()
        }

        btnCancel.setOnClickListener {
            finish()
        }

        // -----------------------

    }
}