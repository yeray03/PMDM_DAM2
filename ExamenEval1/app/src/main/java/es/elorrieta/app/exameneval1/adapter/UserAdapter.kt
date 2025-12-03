package es.elorrieta.app.exameneval1.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.elorrieta.app.exameneval1.R
import es.elorrieta.app.exameneval1.room.entitys.Usuario

class UserAdapter(private var usuarios: MutableList<Usuario>, private val context: Context) :

    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // Un companion object es la forma de Kotlin de hacer statics en Java
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    // ViewHolder para Empleados
    class EmpleadoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.id)
        val login: TextView = view.findViewById(R.id.login)
        val pass: TextView = view.findViewById(R.id.pass)
        val nombre: TextView = view.findViewById(R.id.nombre)
        val empresa: TextView = view.findViewById(R.id.empresa)
    }

    // ViewHolder para header
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.id)
        val login: TextView = view.findViewById(R.id.login)
        val pass: TextView = view.findViewById(R.id.pass)
        val nombre: TextView = view.findViewById(R.id.nombre)
        val empresa: TextView = view.findViewById(R.id.empresa)
    }

    // Devuelve el tipo de vista segun la posicion
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    // Llamado cuando RecyclerView necesita un nuevo RecyclerView (Un Header o una fila normal)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER)
            R.layout.user_item_header
        else
            R.layout.user_item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return if (viewType == TYPE_HEADER) HeaderViewHolder(view)
        else EmpleadoViewHolder(view)
    }

    // Muestra los datos en una posicion especifica
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(
            "Adapter",
            "onBindViewHolder position=$position, type=${getItemViewType(position)}"
        )
        if (holder is HeaderViewHolder) {
            holder.id.text = context.getString(R.string.id)
            holder.login.text = context.getString(R.string.login)
            holder.pass.text = context.getString(R.string.pass)
            holder.nombre.text = context.getString(R.string.nombre)
            holder.empresa.text = context.getString(R.string.empresa)
        } else if (holder is EmpleadoViewHolder) {
            val empleado = usuarios[position - 1]
            Log.d("Adapter", "Asignando empleado: ${empleado.nombre}")
            holder.id.text = empleado.id.toString()
            holder.login.text = empleado.login
            holder.pass.text = empleado.pass
            holder.nombre.text = empleado.nombre
            holder.empresa.text = empleado.empresa
        }
    }

    // Devuelve el numero total de items en el conjunto de datos mantenido por el adaptador (+1 para el header)
    override fun getItemCount(): Int {
        val count = usuarios.size + 1
        Log.d("Adapter", "getItemCount = $count")
        return count
    }

    // Actualiza la lista de empleados y notifica al adaptador
    fun update(newUsuarios: MutableList<Usuario>) {
        Log.d("Adapter", "Actualizando adapter con ${newUsuarios.size} usuarios")
        usuarios = newUsuarios
        notifyDataSetChanged()
    }
}

