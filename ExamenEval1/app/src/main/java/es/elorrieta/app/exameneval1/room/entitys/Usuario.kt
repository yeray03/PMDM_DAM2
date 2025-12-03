package es.elorrieta.app.exameneval1.room.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "t_usuarios")
data class Usuario(
    @PrimaryKey (autoGenerate = true)
    val id : Int,
    val login: String,
    val pass : String,
    val nombre: String,
    val empresa: String
) : Serializable
