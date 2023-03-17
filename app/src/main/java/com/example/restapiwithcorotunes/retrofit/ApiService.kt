package com.example.restapiwithcorotunes.retrofit

import com.example.restapiwithcorotunes.models.MyPostToDoRequest
import com.example.restapiwithcorotunes.models.MyPostTodoResponse
import com.example.restapiwithcorotunes.models.MyTodo
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("plan")
    suspend fun getAllTodo(): List<MyTodo>

    @POST("plan/")
    suspend fun addTodo(@Body myPostToDoRequest: MyPostToDoRequest): MyPostTodoResponse

    @PUT("plan/{id}/")
    suspend fun updateToDo(
        @Path("id") id: Int,
        @Body myPostToDoRequest: MyPostToDoRequest,
    ): MyPostTodoResponse

    @DELETE("plan/{id}/")
    suspend fun deleteToDo(@Path("id") id: Int)
}