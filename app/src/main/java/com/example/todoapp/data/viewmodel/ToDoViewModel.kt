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
import com.example.todoapp.usecase.ToDoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application,
                    private val toDoRepository: ToDoRepository
): AndroidViewModel(application) {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _dataLoadError = MutableLiveData<Boolean>()
    val dataLoadError: LiveData<Boolean> get() = _dataLoadError

    private val _data = MutableLiveData<List<ToDoData>>()
    val data: LiveData<List<ToDoData>> get() = _data


    fun insertData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.insertData(toDoData)
        }
    }

    fun getAllData(){
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO){
            toDoRepository.getAllData()?.let {
                val data = ToDoDatabase(getApplication()).toDoDao().getAllData()
                dataRetrieved(data)
            } ?: run {
                dataNotRetrieved()
            }

        }
    }

    private fun dataRetrieved(toDoList: List<ToDoData>) {
        _data.postValue(toDoList)
        _dataLoadError.postValue(false)
        _loading.postValue(false)
    }

    private fun dataNotRetrieved() {
        _dataLoadError.postValue(true)
        _loading.postValue(false)
    }

}