package com.example.ejercicioroom.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ejercicioroom.room.entitys.Departamento

@Dao
interface DepartamentoDAO {

    @Query("SELECT * FROM t_departamentos")
    fun getAllDepartamentos(): List<Departamento> // Obtiene todos los departamento

    @Query("SELECT * FROM t_departamentos WHERE id = :idInput")
    fun getDepartamentoById(idInput: Int): Departamento // Obtiene un departamento por su ID

    @Insert
    fun insertDepartamento(vararg departamento: Departamento) // Inserta 1:n departamentos

    @Update
    fun updateDepartamento(departamento: Departamento) // Actualiza la información de un departamento

    @Delete
    fun deleteDepartamento(departamento: Departamento) // Elimina un departamento

    @Query("DELETE FROM t_departamentos")
    fun deleteAllDepartamentos() // Elimina todos los departamentos
}