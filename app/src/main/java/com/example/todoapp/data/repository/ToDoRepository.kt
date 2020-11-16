package com.example.todoapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.todoapp.data.ToDoDao
import com.example.todoapp.data.ToDoDatabase
import com.example.todoapp.data.models.ToDoData

class ToDoRepository(context: Context) {

    private val toDoDao by lazy { ToDoDatabase(context).toDoDao() }
    val sortByHighPriority: LiveData<List<ToDoData>> = toDoDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<ToDoData>> = toDoDao.sortByLowPriority()

    suspend fun getAllData():List<ToDoData> = toDoDao.getAllData()
    suspend fun insertData(toDoData: ToDoData) = toDoDao.inserData(toDoData)
    suspend fun updateData(toDoData: ToDoData) = toDoDao.updateData(toDoData)
    suspend fun deleteData(toDoData: ToDoData) = toDoDao.deleteData(toDoData)
    suspend fun deleteAllData() = toDoDao.deleteAllData()
    fun searchDatabase(searchQuery: String) = toDoDao.searchDatabase(searchQuery)

}