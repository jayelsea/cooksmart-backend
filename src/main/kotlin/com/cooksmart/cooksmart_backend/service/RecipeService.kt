package com.cooksmart.service

import com.cooksmart.model.Recipe
import com.cooksmart.repository.RecipeRepository
import org.springframework.stereotype.Service

@Service
class RecipeService(private val recipeRepository: RecipeRepository) {

    fun getAllRecipes(): List<Recipe> = recipeRepository.findAll()

    fun getRecipeById(id: Long): Recipe =
        recipeRepository.findById(id).orElseThrow { RuntimeException("Recipe not found") }

    fun createRecipe(recipe: Recipe): Recipe = recipeRepository.save(recipe)

    fun updateRecipe(id: Long, updatedRecipe: Recipe): Recipe {
        val recipe = getRecipeById(id)
        val recipeToSave = recipe.copy(
            title = updatedRecipe.title,
            ingredients = updatedRecipe.ingredients,
            instructions = updatedRecipe.instructions,
            calories = updatedRecipe.calories,
            country = updatedRecipe.country
        )
        return recipeRepository.save(recipeToSave)
    }

    fun deleteRecipe(id: Long) = recipeRepository.deleteById(id)
}
