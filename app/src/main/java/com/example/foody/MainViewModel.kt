package com.example.foody

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foody.data.Repository
import com.example.foody.di.NetworkResult
import com.example.foody.models.FoodRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

//extend it with androidViewModel
//inject repository class in MainViewModel so specify @HiltViewModel and @Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: MyApplication
) : AndroidViewModel(application) {

    //mutable liveData object (FoodRecipe) wrapped inside NetworkResult
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    //passing queries of type map and use viewModelScope to launch coroutine
    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        //new function to check the safe call and pass the queries
        getRecipesSafeCall(queries)
    }

    //requesting data from api with getRecipes() from remote data source and handling error message
    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        //everyTime getRecipesSafeCall is called response it with loading state and when get actual data from api, go with success or error
        recipesResponse.value = NetworkResult.Loading()

        //if internet connection is true, then make a get Request to API and store result inside recipesResponse (Mutable liveData object)
        //else , Set recipesResponse value to networkResult of type error and pass a parameter or message (no internet connection)
        if (hasInternetConnection()) {
            try {
                //create a val response and get the value by using repository to call remote to get access of remote data source then call getRecipes()
                val response = repository.remote.getRecipes(queries)
                //set recipes value by calling handleFoodRecipesResponse and pass the response(response fetched from api) to it
                recipesResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                //set recipesResponse value to NetworkResult of type ERROR and pass error Message
                recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    //this function take response from API and handle and parse a response
    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            //if response message contains timeout return networkResult of type ERROR and pass message timeout
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            //if response code is 402  return networkResult of type ERROR and pass message API key limited
            response.code() == 402 -> {
                return NetworkResult.Error("API key Limited")
            }
            //if response body contains empty or null return networkResult of type ERROR and pass message recipes not found
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found")
            }
            //if response is successful return full recipe from api
            response.isSuccessful -> {
                //contains result from response
                val foodRecipe = response.body()
                //return networkResult of Success and pass parameters foodRecipe data
                return NetworkResult.Success(foodRecipe!!)
            }
            //return networkResult of type ERROR and pass the response message
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    //function to check internet connection and return true or false
    private fun hasInternetConnection(): Boolean {
        //requesting connectivity service
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        //get active network
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        //get network capabilities
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}