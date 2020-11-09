package com.example.todoapp.di


import com.example.todoapp.data.repository.ToDoRepository
import com.example.todoapp.data.viewmodel.ToDoViewModel
import com.example.todoapp.fragment.SharedViewModel
import com.example.todoapp.fragment.add.AddFragment
import com.example.todoapp.fragment.list.ListFragment
import com.example.todoapp.fragment.update.UpdateFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.math.sin


object Module {
    val moduleDI = module{
        single { ListFragment() }
        single { AddFragment() }
        single { UpdateFragment() }
        single { ToDoRepository(context = get()) }
        viewModel { ToDoViewModel(application = get(), toDoRepository = get()) }
        viewModel { SharedViewModel(application = get()) }
    }

}