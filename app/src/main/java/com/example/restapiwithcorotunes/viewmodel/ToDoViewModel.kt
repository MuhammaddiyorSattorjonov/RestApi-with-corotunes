package com.example.restapiwithcorotunes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapiwithcorotunes.models.MyPostToDoRequest
import com.example.restapiwithcorotunes.models.MyPostTodoResponse
import com.example.restapiwithcorotunes.models.MyTodo
import com.example.restapiwithcorotunes.repository.ToDoRepository
import com.example.restapiwithcorotunes.retrofit.ApiClient
import com.example.restapiwithcorotunes.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ToDoViewModel(val toDoRepository: ToDoRepository) : ViewModel() {
    private val liveData = MutableLiveData<Resource<List<MyTodo>>>()


    fun getAllTodo(): MutableLiveData<Resource<List<MyTodo>>> {
        viewModelScope.launch {
            liveData.postValue(Resource.loading("message"))

            try {

                coroutineScope {

                    val list = async {
                        toDoRepository.getTodoApi()
                    }.await()

                    liveData.postValue(Resource.succes(list))
                }
            } catch (e: Exception) {
                liveData.postValue(Resource.error(e.message))
            }
        }
        return liveData
    }

    private val postLiveData = MutableLiveData<Resource<MyPostTodoResponse>>()
    fun addMyTodo(myPostToDoRequest: MyPostToDoRequest): MutableLiveData<Resource<MyPostTodoResponse>> {
        viewModelScope.launch {
            postLiveData.postValue(Resource.loading("Loading Post"))
            try {
                coroutineScope {
                    val response = async {
                        toDoRepository.addToDo(myPostToDoRequest)
                    }.await()
                    postLiveData.postValue(Resource.succes(response))
                    getAllTodo()
                }
            } catch (e: Exception) {
                postLiveData.postValue(Resource.error(e.message))
            }
        }
        return postLiveData
    }

    private val liveDataUpdate = MutableLiveData<Resource<MyPostTodoResponse>>()
    fun updateMyToDo(
        id: Int,
        myPostToDoRequest: MyPostToDoRequest,
    ): MutableLiveData<Resource<MyPostTodoResponse>> {

        viewModelScope.launch {
            liveDataUpdate.postValue(Resource.loading("Loading Update"))
            try {
                coroutineScope {
                    val response = async {
                        toDoRepository.updateToDo(id, myPostToDoRequest)
                    }.await()
                    liveDataUpdate.postValue(Resource.succes(response))
                    getAllTodo()
                }
            } catch (e: Exception) {
                liveDataUpdate.postValue(Resource.error(e.message))
            }
        }

        return liveDataUpdate
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val response = launch {
                        toDoRepository.deleteToDo(id)
                    }
                    getAllTodo()
                }
            } catch (e: Exception) {
            }
        }
    }
}