package com.cooksmart.controller

import com.cooksmart.model.Recipe
import com.cooksmart.model.api.RecipeResponse
import com.cooksmart.service.RecipeApiService
import com.cooksmart.service.RecipeService
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/recipes")
class RecipeController(
    private val recipeService: RecipeService,
    private val recipeApiService: RecipeApiService
) {

    // CRUD estándar para Recipe
    @GetMapping
    fun getAllRecipes(): List<Recipe> = recipeService.getAllRecipes()

    @GetMapping("/{id}")
    fun getRecipeById(@PathVariable id: Long): Recipe = recipeService.getRecipeById(id)

    @PostMapping
    fun createRecipe(@RequestBody recipe: Recipe): Recipe = recipeService.createRecipe(recipe)

    @PutMapping("/{id}")
    fun updateRecipe(@PathVariable id: Long, @RequestBody recipe: Recipe): Recipe =
        recipeService.updateRecipe(id, recipe)

    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Long) = recipeService.deleteRecipe(id)

    // Nuevo endpoint para consumir la API externa Spoonacular
    @GetMapping("/external")
    fun getExternalRecipes(@RequestParam ingredients: String): List<RecipeResponse> = runBlocking {
        recipeApiService.getRecipesByIngredients(ingredients)
    }
}
