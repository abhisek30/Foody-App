package com.example.foody

import com.example.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

//fetch data from foodRecipesApi(Spoonacular Api)
//by annotating inject Constructor and specifying type ,which will inject hilt will search for this specific type in network Module
//and search for a function with same type(In this case FoodRecipesApi) and that's how foodRecipesApi instance is created
class RemoteDataSource  @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
){
    //getRecipes() - queries to handle multiple queries to handle get Request
    // and return a FoodRecipe(Model class) wrapped inside Response (part of retrofit library)
    //getRecipes() is fetched from FoodRecipesApi interface
    suspend fun getRecipes(queries:Map<String,String>):Response<FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)
    }
}