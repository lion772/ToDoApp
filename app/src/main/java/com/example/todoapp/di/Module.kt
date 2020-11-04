package com.example.todoapp.di


import com.example.todoapp.data.repository.ToDoRepository
import com.example.todoapp.data.viewmodel.ToDoViewModel
import com.example.todoapp.fragment.SharedViewModel
import com.example.todoapp.fragment.add.AddFragment
import com.example.todoapp.fragment.list.ListFragment
import com.example.todoapp.usecase.ToDoUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object Module {
    val moduleDI = module{
        single { ListFragment() }
        single { AddFragment() }
        single { ToDoRepository(context = get()) }
        single { ToDoUseCase(toDoRepository = get()) }
        viewModel { ToDoViewModel(application = get(), toDoRepository = get()) }
        viewModel { SharedViewModel(application = get()) }
    }

}