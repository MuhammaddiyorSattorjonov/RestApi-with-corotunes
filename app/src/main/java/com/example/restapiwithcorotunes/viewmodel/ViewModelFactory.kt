package com.example.restapiwithcorotunes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restapiwithcorotunes.repository.ToDoRepository

class ViewModelFactory(val toDoRepository: ToDoRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoViewModel::class.java)){
            return ToDoViewModel(toDoRepository) as T
        }
        throw java.lang.IllegalArgumentException("Error")
        return super.create(modelClass)
    }
}