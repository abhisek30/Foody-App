package com.example.foody.di

//use generics to create this class
//Network generic class will contains 3 different classes
//add two parameters the data which is fetched from Api and the message(Success or Fail)
//use generics for data and type will be a nullable and message of type string and mark nullable as well
sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    //generic of type t success class and return NetworkResult type t and pass data to it
    class Success<T>(data: T) : NetworkResult<T>(data)
    //generic of type t error class and pass message and data(as there is a error so data is null) to it
    //and return NetworkResult of type t and pass data and message here
    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)
    //generic of type t Loading class and no parameters and return NetworkResult of type t
    class Loading<T> : NetworkResult<T>()
}