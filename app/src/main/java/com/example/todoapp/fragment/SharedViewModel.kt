package com.example.todoapp.fragment

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoData
import com.example.todoapp.fragment.add.AddFragment.Companion.FIRST_POSITION
import com.example.todoapp.fragment.add.AddFragment.Companion.HIGH_PRIORITY
import com.example.todoapp.fragment.add.AddFragment.Companion.LOW_PRIORITY
import com.example.todoapp.fragment.add.AddFragment.Companion.MEDIUM_PRIORITY
import com.example.todoapp.fragment.add.AddFragment.Companion.SECOND_POSITION
import com.example.todoapp.fragment.add.AddFragment.Companion.THIRD_POSITION

class SharedViewModel(application: Application): AndroidViewModel(application) {

    private val _emptyDatabase = MutableLiveData<Boolean>()
    val emptyDatabase: LiveData<Boolean> get() = _emptyDatabase

    fun checkIfDatabaseEmpty(toDoData: List<ToDoData>){
        _emptyDatabase.value = toDoData.isEmpty()
    }

    val listener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
            when(position){
                FIRST_POSITION -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))}
                SECOND_POSITION -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))}
                THIRD_POSITION -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))}
            }
        }
    }

    fun verifyDataFromUser(title: String, description: String): Boolean {
        return when {
            TextUtils.isEmpty(title) || TextUtils.isEmpty(description) -> false
            else -> true
        }
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            HIGH_PRIORITY -> Priority.HIGH
            MEDIUM_PRIORITY -> Priority.MEDIUM
            LOW_PRIORITY -> Priority.LOW
            else -> Priority.HIGH
        }
    }
}