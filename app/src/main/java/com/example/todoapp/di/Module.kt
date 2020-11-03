package com.example.todoapp.di


import org.koin.dsl.module


object Module {
    val moduleDI = module{
        //single { ToDoRepository(context = get(), toDoDao = get()) }
        //single { ListFragment() }
        //viewModel { ToDoViewModel(get())}
    }

}