package com.example.restapiwithcorotunes.utils

import com.example.restapiwithcorotunes.models.MyTodo

data class Resource<out T>(val status:Status, val data:T?, val message: String?) {

    companion object{
        fun<T>succes(data:T):Resource<T>{
            return Resource(Status.SUCCES,data,null)
        }

        fun<T>error(message:String?):Resource<T>{
            return Resource(Status.ERROR,null,message)
        }

        fun<T>loading(message: String?):Resource<T>{
            return Resource(Status.LOADING,null,message)
        }
    }
}
