package com.cooksmart.model.api

data class Ingredient(
    val id: Int,
    val name: String,
    val original: String,
    val originalString: String,
    val amount: Double,
    val unit: String
)
