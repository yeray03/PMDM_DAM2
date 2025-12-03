package com.example.ejercicioroom.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicioroom.room.entitys.Empleado
import com.example.ejercicioroom.R

class EmpleadosAdapter(private var empleados: MutableList<Empleado>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Un companion object es la forma de Kotlin de hacer statics en Java
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    // ViewHolder para Empleados
    class EmpleadoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.empId)
        val nombre: TextView = view.findViewById(R.id.empNombre)
        val puesto: TextView = view.findViewById(R.id.empPuesto)
        val salario: TextView = view.findViewById(R.id.empSalario)
        val departamentoId: TextView = view.findViewById(R.id.empDeptId)
    }

    // ViewHolder para header
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.empId)
        val nombre: TextView = view.findViewById(R.id.empNombre)
        val puesto: TextView = view.findViewById(R.id.empPuesto)
        val salario: TextView = view.findViewById(R.id.empSalario)
        val departamentoId: TextView = view.findViewById(R.id.empDeptId)
    }

    // Devuelve el tipo de vista segun la posicion
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    // Llamado cuando RecyclerView necesita un nuevo RecyclerView (Un Header o una fila normal)
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER)
            R.layout.item_empleado_header
        else
            R.layout.item_empleado

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_empleado, parent, false)

        return if (viewType == TYPE_HEADER) HeaderViewHolder(view)
        else EmpleadoViewHolder(view)
    }

    // Muestra los datos en una posicion especifica
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder,position: Int) {
        Log.d("Adapter", "onBindViewHolder position=$position, type=${getItemViewType(position)}")

        if (holder is HeaderViewHolder) {
            holder.id.text = context.getString(R.string.id)
            holder.nombre.text = context.getString(R.string.nombre)
            holder.puesto.text = context.getString(R.string.puesto)
            holder.salario.text = context.getString(R.string.salario)
            holder.departamentoId.text = context.getString(R.string.dept_id)
        } else if (holder is EmpleadoViewHolder) {
            val empleado = empleados[position - 1]
            Log.d("Adapter", "Asignando empleado: ${empleado.nombre}")
            holder.id.text = empleado.id.toString()
            holder.nombre.text = empleado.nombre
            holder.puesto.text = empleado.puesto
            holder.salario.text = empleado.salario.toString()
            holder.departamentoId.text = empleado.departamentoId.toString()

        }
    }
    // Devuelve el numero total de items en el conjunto de datos mantenido por el adaptador (+1 para el header)
    override fun getItemCount(): Int {
        val count = empleados.size + 1
        Log.d("Adapter", "getItemCount = $count")
        return count
    }

    // Actualiza la lista de empleados y notifica al adaptador
    fun update(newEmpleados: MutableList<Empleado>) {
        Log.d("Adapter", "Actualizando adapter con ${newEmpleados.size} empleados")
        empleados = newEmpleados
        notifyDataSetChanged()
    }
}
