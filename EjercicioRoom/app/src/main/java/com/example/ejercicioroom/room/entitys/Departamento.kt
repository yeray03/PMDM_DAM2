package com.example.ejercicioroom.room.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_departamentos")
data class Departamento(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val nombre:String,
    val localidad:String
)
