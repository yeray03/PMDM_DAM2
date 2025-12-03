package com.example.ejercicioroom

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicioroom.room.RoomDB
import com.example.ejercicioroom.adapter.EmpleadosAdapter
import com.example.ejercicioroom.room.entitys.Departamento
import com.example.ejercicioroom.room.entitys.Empleado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        // instanciamos la base de datos
        val db = RoomDB(this)
        // las consultas a la base de datos han de hacerse en un hilo aparte
        lifecycleScope.launch(Dispatchers.IO) {
            var empleados = db.getEmpleadoDao().getAllEmpleados()
            if (empleados.isEmpty()) {
                // insertamos departamentos por defecto
                db.getDepartamentoDao().insertDepartamento(
                    Departamento(
                        id = 0,
                        nombre = "Desarrollo",
                        localidad = "Madrid"
                    ),
                    Departamento(
                        id = 0,
                        nombre = "Diseño",
                        localidad = "Barcelona"
                    ),
                    Departamento(
                        id = 0,
                        nombre = "Recursos Humanos",
                        localidad = "Valencia"
                    )
                )
                // insertamos empleados por defecto
                db.getEmpleadoDao().insertEmpleado(
                    Empleado(
                        id = 0,
                        nombre = "Yeray",
                        puesto = "Programador",
                        salario = 1200.0,
                        departamentoId = 1
                    ),
                    Empleado(
                        id = 0,
                        nombre = "Ruben",
                        puesto = "Diseñador",
                        salario = 1100.0,
                        departamentoId = 2
                    ),
                    Empleado(
                        id = 0,
                        nombre = "Ivan",
                        puesto = "Analista",
                        salario = 1300.0,
                        departamentoId = 1
                    )
                )
            }
            empleados = db.getEmpleadoDao().getAllEmpleados()
            Log.i("EMPLEADOS", "Empleados:")
            empleados.forEach { Log.i("EMPLEADO", "$it") }
        }

        // -----------------------

        // El RecyclerView
        val empleadosRecyclerView: RecyclerView = findViewById(R.id.recyclerView)
        empleadosRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        // Cargamos una lista vacía al RecyclerView
        val empleadosAdapter = EmpleadosAdapter(mutableListOf(), this)
        empleadosRecyclerView.adapter = empleadosAdapter

        // El botón de "Get All"
        findViewById<android.widget.Button>(R.id.btnGetAll).setOnClickListener {

            // La única instancia de db (singleton)
//            val db = RoomDB(this)

            // Lanzamos esta parte como una Coroutine. Esto significa, un hilo paralelo a la Activity.
            // Más o menos. Si lo hacemos así, podemos luego actualizar la UI fácilmente, y este hilo
            // muere siempre que la Activity también muere
            lifecycleScope.launch(Dispatchers.IO) {
                // Obtenemos el EmpleadoDao y luego llamamos al metodo getAllEmpleados
                val list = db.getEmpleadoDao().getAllEmpleados()

                // Hacemos una Mutable List
                val empleadoList = list.toMutableList()

                // De nuevo, cosas de Coroutines. Así podemos actualizar el adaptador
                launch(Dispatchers.Main) {
                    // Actualizamos el adaptador
                    empleadosAdapter.update(empleadoList)
                }
            }
        }
        // -----------------------

        // El botón de "Add Empleado"
        findViewById<android.widget.Button>(R.id.btnInsertEmpleado).setOnClickListener {
            // La única instancia de db (singleton)
//            val db = RoomDB(this)

            // Lanzamos esta parte como una Coroutine. Esto significa, un hilo paralelo a la Activity.
            // Más o menos. Si lo hacemos así, podemos luego actualizar la UI fácilmente, y este hilo
            // muere siempre que la Activity también muere
            lifecycleScope.launch(Dispatchers.IO) {

                // Añadimos uno nuevo...
                db.getEmpleadoDao().insertEmpleado(
                    Empleado(
                        id = 0,
                        nombre = "Marta",
                        puesto = "INSERT",
                        salario = 1000.0,
                        departamentoId = 1
                    )
                )

                // Vamos a actualizar el adaptador...
                val list = db.getEmpleadoDao().getAllEmpleados()
                val empleadoList = list.toMutableList()

                // De nuevo, cosas de Coroutines. Así podemos actualizar el adaptador
                launch(Dispatchers.Main) {
                    // Actualizamos el adaptador
                    empleadosAdapter.update(empleadoList)
                }
            }
        }
        // -----------------------

        // El botón de "Delete All Empleados"
        findViewById<android.widget.Button>(R.id.btnDeleteEmp).setOnClickListener {
            // La única instancia de db (singleton)
//            val db = RoomDB(this)
            // Lanzamos esta parte como una Coroutine. Esto significa, un hilo paralelo a la Activity.
            // Más o menos. Si lo hacemos así, podemos luego actualizar la UI fácilmente, y este hilo
            // muere siempre que la Activity también muere
            lifecycleScope.launch(Dispatchers.IO) {
                // Eliminamos todos los empleados
                db.getEmpleadoDao().deleteAllEmpleados()

                // Vamos a actualizar el adaptador...
                val list = db.getEmpleadoDao().getAllEmpleados()
                val empleadoList = list.toMutableList()

                // De nuevo, cosas de Coroutines. Así podemos actualizar el adaptador
                launch(Dispatchers.Main) {
                    // Actualizamos el adaptador
                    empleadosAdapter.update(empleadoList)
                }
            }
        }
    }
}