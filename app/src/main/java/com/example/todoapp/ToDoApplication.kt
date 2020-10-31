package com.example.todoapp

import android.app.Application
import com.example.todoapp.di.Module.moduleDI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class ToDoApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ToDoApplication)
            modules(listOf(moduleDI))
        }
    }
}