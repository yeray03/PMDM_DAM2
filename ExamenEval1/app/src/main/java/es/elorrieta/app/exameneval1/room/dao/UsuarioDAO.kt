package es.elorrieta.app.exameneval1.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.elorrieta.app.exameneval1.room.entitys.Usuario

@Dao
interface UsuarioDAO {
    @Query ("SELECT * FROM t_usuarios")
    fun getAllUsers(): List<Usuario>

    @Query ("SELECT * FROM t_usuarios where login = :loginInput")
    fun getUserByLogin(loginInput: String): Usuario?
    @Insert
    fun insertUser(vararg usuario: Usuario)
}