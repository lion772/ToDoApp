package com.example.todoapp.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.internal.synchronized

@Database(entities = [ToDoData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase: RoomDatabase() {

    abstract fun toDoDao():ToDoDao

    companion object{
        @Volatile private var INSTANCE: ToDoDatabase? = null
        private val LOCK = Any()

        fun getDatabase(context: Context) =
            INSTANCE ?: kotlin.synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, ToDoDatabase::class.java, "dog_database"
        ).build()
    }


}