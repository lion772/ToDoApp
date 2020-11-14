package com.example.todoapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.ToDoDao
import com.example.todoapp.data.ToDoDatabase
import com.example.todoapp.data.models.ToDoData
import com.example.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(
    application: Application,
    private val toDoRepository: ToDoRepository
) : AndroidViewModel(application) {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _data = MutableLiveData<List<ToDoData>>()
    val data: LiveData<List<ToDoData>> get() = _data


    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.insertData(toDoData)
        }
    }

    fun getAllData() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val data = toDoRepository.getAllData()
            dataRetrieved(data)
        }
    }

    fun updateData(toDoData: ToDoData) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.updateData(toDoData)
        }
    }

    fun deleteData(toDoData: ToDoData) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.deleteData(toDoData)
            _loading.postValue(false)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.deleteAllData()
        }
    }

    private fun dataRetrieved(toDoList: List<ToDoData>) {
        _data.postValue(toDoList)
        _loading.postValue(false)
    }

    fun searchDatabase(searchQuery:String) = toDoRepository.searchDatabase(searchQuery)

}