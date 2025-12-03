package com.example.ejercicioroom.room.entitys

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey


@Entity (tableName = "t_empleados",
    foreignKeys = [
        ForeignKey(
            entity = Departamento::class,
            parentColumns = ["id"],
            childColumns = ["departamentoId"],
            onDelete = CASCADE
        )
    ]
)

data class Empleado(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nombre: String,
    val puesto: String,
    val salario: Double,
    val departamentoId: Int
)
