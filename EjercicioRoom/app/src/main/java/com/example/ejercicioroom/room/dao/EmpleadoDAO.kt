package com.example.ejercicioroom.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ejercicioroom.room.entitys.Empleado

@Dao
interface EmpleadoDAO {

    @Query("SELECT * FROM t_empleados")
    fun getAllEmpleados(): List<Empleado> // Obtiene todos los empleados

    @Query("SELECT * FROM t_empleados WHERE id = :idInput")
    fun getEmpleadoById(idInput: Int): Empleado // Obtiene un empleado por su ID

    @Query("SELECT * FROM t_empleados WHERE departamentoId = :inputDepartamentoId")
    fun getEmpleadosByDepartamento(inputDepartamentoId: Int): List<Empleado> // Obtiene empleados por ID de departamento

    @Insert
    fun insertEmpleado(vararg empleado: Empleado) // Inserta 1:n empleados

    @Update
    fun updateEmpleado(empleado: Empleado) // Actualiza la información de un empleado

    @Delete
    fun deleteEmpleado(empleado: Empleado) // Elimina un empleado

    @Query("DELETE FROM t_empleados")
    fun deleteAllEmpleados() // Elimina todos los empleados
}