package com.example.pokemonbox.data.network.model

sealed interface NetworkResponse<out T> {

    data class Success<out T>(val data: T) : NetworkResponse<T>

    data class Error(val code: Int, val message: String?) : NetworkResponse<Nothing>

    data class Exception(val throwable: Throwable) : NetworkResponse<Nothing>
}