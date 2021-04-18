package com.example.foody.di

import com.example.foody.util.Constants.Companion.BASE_URL
import com.example.foody.data.network.FoodRecipesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

//networkModule where we will tell hiltLibrary how to initialize retrofitBuilder(FoodRecipesApi)
//all binding inside Network Module will available inside Singleton Component
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //to provide okHttpClient in provideRetrofitInstance fun, return OkHttpClient builder
    @Singleton
    @Provides
    fun provideHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(15,TimeUnit.SECONDS)
            .connectTimeout(15,TimeUnit.SECONDS)
            .build()
    }

    //to provide gsonConvertorFactory in provideRetrofitInstance fun, return GsonConvertorFactory
    @Singleton
    @Provides
    fun provideConverterFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    //for creating retrofit instance, pass dependencies - okHttpClient and gsonConverterFactoru
    //gsonConverter factory is fetched by providerConverterFactory() so its passing the parameter from that
    //use provides so that hilt can know where to fetch the gsonConverterFactory
    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    //by specifying FoodRecipesApi return type ,telling hilt library which class going to inject later
    //@Singleton - using application scope for foodRecipeApi so specifying that
    //@Provides - using retrofit library which is a external library so specifying that
    //hilt library knows that provideRetrofitInstance is returning Retrofit so use the parameter from that function return
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FoodRecipesApi {
        return retrofit.create(FoodRecipesApi::class.java)
    }
}