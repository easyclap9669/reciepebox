package com.example.reciepebox.api

import com.example.reciepebox.model.Recipe
import com.example.reciepebox.model.RecipesResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {

    @GET("recipes")
    suspend fun getRecipes(
        @Query("q") query: String? = null,
        @Query("tag") tag: String? = null
    ): RecipesResponse

    @GET("recipes/{id}")
    suspend fun getRecipe(
        @Path("id") id: Int
    ): Recipe

    @POST("recipes")
    suspend fun createRecipe(
        @Body recipe: Recipe
    ): Recipe

    @PUT("recipes/{id}")
    suspend fun updateRecipe(
        @Path("id") id: Int,
        @Body recipe: Recipe
    ): Recipe

    @DELETE("recipes/{id}")
    suspend fun deleteRecipe(
        @Path("id") id: Int
    )
}
