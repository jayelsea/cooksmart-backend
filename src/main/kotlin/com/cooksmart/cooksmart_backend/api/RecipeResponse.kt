package com.cooksmart.model.api

data class RecipeResponse(
    val id: Int,
    val title: String,
    val image: String,
    val imageType: String,
    val usedIngredientCount: Int,
    val missedIngredientCount: Int,
    val missedIngredients: List<Ingredient>,
    val usedIngredients: List<Ingredient>,
    val likes: Int
)
