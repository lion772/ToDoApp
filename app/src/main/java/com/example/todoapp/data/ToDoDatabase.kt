package com.example.todoapp.data

import android.content.Context
import androidx.room.*
import com.example.todoapp.data.models.ToDoData

@Database(entities = [ToDoData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase: RoomDatabase() {

    abstract fun toDoDao():ToDoDao

    companion object{
        @Volatile private var INSTANCE: ToDoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) =
            INSTANCE ?: synchronized(LOCK) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, ToDoDatabase::class.java, "dog_database"
        ).build()
    }


}