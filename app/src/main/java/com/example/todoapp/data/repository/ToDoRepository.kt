package com.example.todoapp.data.repository

import android.content.Context
import com.example.todoapp.data.ToDoDao
import com.example.todoapp.data.ToDoDatabase
import com.example.todoapp.data.models.ToDoData

class ToDoRepository(context: Context) {

    private val toDoDao by lazy { ToDoDatabase(context).toDoDao() }

    suspend fun getAllData():List<ToDoData>? = toDoDao.getAllData()
    suspend fun insertData(toDoData: ToDoData) = toDoDao.inserData(toDoData)
    suspend fun updateData(toDoData: ToDoData) = toDoDao.updateData(toDoData)


}