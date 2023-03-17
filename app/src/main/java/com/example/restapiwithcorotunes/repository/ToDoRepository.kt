package com.example.restapiwithcorotunes.repository

import com.example.restapiwithcorotunes.models.MyPostToDoRequest
import com.example.restapiwithcorotunes.retrofit.ApiService

class ToDoRepository(val apiService: ApiService) {

    suspend fun getTodoApi() = apiService.getAllTodo()
    suspend fun addToDo(myPostToDoRequest: MyPostToDoRequest) =
        apiService.addTodo(myPostToDoRequest)

    suspend fun updateToDo(id: Int, myPostToDoRequest: MyPostToDoRequest) =
        apiService.updateToDo(id, myPostToDoRequest)

    suspend fun deleteToDo(id: Int) = apiService.deleteToDo(id)
}