package es.elorrieta.app.exameneval1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import es.elorrieta.app.exameneval1.adapter.SpinnerAdapter
import es.elorrieta.app.exameneval1.adapter.UserAdapter
import es.elorrieta.app.exameneval1.room.RoomDB
import es.elorrieta.app.exameneval1.room.entitys.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Your code here! ---//
        val extras = intent.extras
        @Suppress("DEPRECATION")
        val usuario = extras?.getSerializable("usuario") as? Usuario
        val loginText = findViewById<TextView>(R.id.editTextTextLoginActivityUser)

        loginText.text = usuario?.login.toString()

        val btnMostrar = findViewById<Button>(R.id.btnMostrar)

        // El RecyclerView
        val empleadosRecyclerView: RecyclerView = findViewById(R.id.recyclerView)
        empleadosRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        // Cargamos una lista vacía al RecyclerView
        val empleadosAdapter = UserAdapter(mutableListOf(), this)
        empleadosRecyclerView.adapter = empleadosAdapter

        // El botón de "Get All"
        findViewById<android.widget.Button>(R.id.btnMostrar).setOnClickListener {

            // La única instancia de db (singleton)
            val db = RoomDB(this)

            // Lanzamos esta parte como una Coroutine. Esto significa, un hilo paralelo a la Activity.
            // Más o menos. Si lo hacemos así, podemos luego actualizar la UI fácilmente, y este hilo
            // muere siempre que la Activity también muere
            lifecycleScope.launch(Dispatchers.IO) {
                // Obtenemos el EmpleadoDao y luego llamamos al metodo getAllEmpleados
                val list = db.getUsuarioDao().getAllUsers()

                // Hacemos una Mutable List
                val empleadoList = list.toMutableList()

                // De nuevo, cosas de Coroutines. Así podemos actualizar el adaptador
                launch(Dispatchers.Main) {
                    // Actualizamos el adaptador
                    empleadosAdapter.update(empleadoList)
                }
            }
        }

        //boton cancelar
        val btnCancelar = findViewById<Button>(R.id.buttonCancelActivityUser)
        btnCancelar.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // boton insertar
        val btnInsert = findViewById<Button>(R.id.btnInsert)
        btnInsert.setOnClickListener {
            val intent = Intent(applicationContext, NewUserActivity::class.java)
            startActivity(intent)
            // no finalizamos actividad para volver despues del insert
        }

        // boton gulugulu
        val btnInternet = findViewById<Button>(R.id.btnInternet)
        btnInternet.setOnClickListener {
            val intent = Intent(applicationContext, GuugleActivity::class.java)
            startActivity(intent)
            // no finalizamos actividad para volver despues a esta actividad
        }

        // cambio de idioma
        // Spinner (menu desplegable) personalizado para seleccionar el idioma
        // NO PREGUNTAR NUNCA COMO FUNCIONA PORQUE NO LO SE
        // FUNCIONA GRACIAS A LA FÉ, LA SUERTE Y ALGUNAS DE MIS LAGRIMAS
        // SI LO ENTIENDES NO FUNCIONA Y SI FUNCIONA NO LO ENTIENDES
        // EDITAR BAJO TU PROPIO RIESGO
        // NO ME HAGO RESPONSABLE DE FUTUROS CRASHEOS SI SE EDITA
        // Fdo.: Yery :)


        val idiomas = listOf(
            R.drawable.icono_espanita_foreground, // posicion 0
            R.drawable.icono_inglish_pitinglish_foreground // posicion 1
        )


        val spinner = findViewById<Spinner>(R.id.idiomas)
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val initialPos = if (prefs.getString("lang","") == "es") 1 else 0
        spinner.adapter = SpinnerAdapter(this, idiomas)
        spinner.setSelection(initialPos)


        @Suppress("DEPRECATION")
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // accede a las preferencias de la app donde se guarda el idioma seleccionado
                val prefs = getSharedPreferences("settings", MODE_PRIVATE)
                val selectedLang = if (position == 0) "es" else "en"
                if (prefs.getString("lang", "") != selectedLang) {
                    prefs.edit { putString("lang", selectedLang) }
                    // crea un objeto Locale con el idioma seleccionado
                    val locale = Locale(selectedLang)
                    val config = resources.configuration
                    config.setLocale(locale)
                    resources.updateConfiguration(config, resources.displayMetrics)
                    // recarga la actividad para que se apliquen los cambios del idioma
                    recreate()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // no hace nada pero es necesario para el override de arriba
            }
        }


    }
}