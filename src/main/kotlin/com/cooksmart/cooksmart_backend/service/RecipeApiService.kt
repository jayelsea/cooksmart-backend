package com.cooksmart.cooksmart_backend.service



import com.cooksmart.model.api.RecipeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface SpoonacularApi {
    @GET("recipes/findByIngredients")
    suspend fun getRecipesByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int = 10,
        @Query("apiKey") apiKey: String
    ): List<RecipeResponse>
}

@Service
class RecipeApiService(
    @Value("\${spoonacular.api.key}") private val apiKey: String
) {
    private val api: SpoonacularApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(SpoonacularApi::class.java)
    }

    suspend fun getRecipesByIngredients(ingredients: String, number: Int = 10): List<RecipeResponse> =
        withContext(Dispatchers.IO) {
            api.getRecipesByIngredients(ingredients, number, apiKey)
        }
}
