package com.example.ejercicioroom.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ejercicioroom.room.dao.DepartamentoDAO
import com.example.ejercicioroom.room.dao.EmpleadoDAO
import com.example.ejercicioroom.room.entitys.Empleado
import com.example.ejercicioroom.room.entitys.Departamento

@Database(entities = [Empleado::class, Departamento::class], version = 1)
abstract class RoomDB : RoomDatabase(){

    // Patron singleton
    companion object{
        @Volatile
        private var instance : RoomDB? = null

        private val LOCK = Any()

        operator fun invoke (context:android.content.Context) = instance?: synchronized(LOCK){
            instance?:buildDatabase (context).also { instance = it}
        }

        private fun buildDatabase (context: android.content.Context) = androidx.room.Room.databaseBuilder(
            context,
            RoomDB::class.java,
            "myDataBase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    // DAOs abstractos. Añadir en funcion de la cantidad de DAOs que tengamos
    abstract fun getEmpleadoDao() : EmpleadoDAO
    abstract fun getDepartamentoDao() : DepartamentoDAO

}