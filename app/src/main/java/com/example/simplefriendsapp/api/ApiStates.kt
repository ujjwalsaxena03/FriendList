package com.example.simplefriendsapp.api

sealed class ApiStates<out T> {
    data class Success<out T>(val data: T) : ApiStates<T>()
    data class Error(val message: String) : ApiStates<Nothing>()
    object Loading : ApiStates<Nothing>()
}