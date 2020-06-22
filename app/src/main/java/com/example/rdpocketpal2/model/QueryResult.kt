package com.example.rdpocketpal2.model

import java.lang.Exception

sealed class QueryResult<out R> {
    /** Successful outcome that stores the result's data */
    data class Success<out T>(val data: T) : QueryResult<T>()
    /** Failed outcome that stores the exception associated with the result */
    data class Failure(val exception: Exception) : QueryResult<Nothing>()
}