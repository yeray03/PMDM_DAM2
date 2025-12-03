package es.elorrieta.app.exameneval1.room

import androidx.room.Database
import androidx.room.RoomDatabase
import es.elorrieta.app.exameneval1.room.dao.UsuarioDAO
import es.elorrieta.app.exameneval1.room.entitys.Usuario

@Database(entities = [Usuario::class], version = 1)
abstract class RoomDB : RoomDatabase(){
    // Patron singleton
    companion object{
        @Volatile
        private var instance : RoomDB? = null
        private val LOCK = Any()
        operator fun invoke (context:android.content.Context) = instance?: synchronized(LOCK){
            instance?:buildDatabase (context).also { instance = it}
        }
        private fun buildDatabase (context: android.content.Context) =
            androidx.room.Room.databaseBuilder(
                context,
                RoomDB::class.java,
                "myDataBase"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
    // DAOs abstractos. Añadir en funcion de la cantidad de DAOs que tengamos
    abstract fun getUsuarioDao() : UsuarioDAO

}
