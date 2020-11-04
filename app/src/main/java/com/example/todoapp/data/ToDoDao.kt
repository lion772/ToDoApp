package com.example.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoapp.data.models.ToDoData

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    suspend fun getAllData(): List<ToDoData>

    @Update
    suspend fun updateData(toDoData: ToDoData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserData(toDoData: ToDoData)


}