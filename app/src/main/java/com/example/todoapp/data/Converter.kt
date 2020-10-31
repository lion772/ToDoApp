package com.example.todoapp.data

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority) = priority.name

    @TypeConverter
    fun toPriority(priority: String) = Priority.valueOf(priority)
}