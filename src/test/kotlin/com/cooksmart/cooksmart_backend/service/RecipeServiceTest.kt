package com.cooksmart.cooksmart_backend.service

import com.cooksmart.model.Recipe
import com.cooksmart.repository.RecipeRepository
import com.cooksmart.service.RecipeService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class RecipeServiceTest {

    @Mock
    private lateinit var recipeRepository: RecipeRepository

    @InjectMocks
    private lateinit var recipeService: RecipeService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `getAllRecipes should return recipes`() {
        val recipes = listOf(Recipe(1, "Pizza", "Cheese", "Bake"))
        `when`(recipeRepository.findAll()).thenReturn(recipes)

        val result = recipeService.getAllRecipes()
        assertEquals(1, result.size)
    }

    @Test
    fun `getRecipeById should return recipe`() {
        val recipe = Recipe(1, "Pizza", "Cheese", "Bake")
        `when`(recipeRepository.findById(1)).thenReturn(Optional.of(recipe))

        val result = recipeService.getRecipeById(1)
        assertEquals("Pizza", result.title)
    }

    @Test
    fun `getRecipeById should throw exception when not found`() {
        `when`(recipeRepository.findById(1)).thenReturn(Optional.empty())
        assertThrows(RuntimeException::class.java) {
            recipeService.getRecipeById(1)
        }
    }

    @Test
    fun `createRecipe should save and return recipe`() {
        val recipe = Recipe(1, "Pizza", "Cheese", "Bake")
        `when`(recipeRepository.save(recipe)).thenReturn(recipe)

        val result = recipeService.createRecipe(recipe)
        assertEquals("Pizza", result.title)
    }

    @Test
    fun `updateRecipe should update and return recipe`() {
        val recipe = Recipe(1, "Pizza", "Cheese", "Bake")
        val updated = Recipe(1, "Burger", "Meat", "Grill")
        `when`(recipeRepository.findById(1)).thenReturn(Optional.of(recipe))
        `when`(recipeRepository.save(any(Recipe::class.java))).thenReturn(updated)

        val result = recipeService.updateRecipe(1, updated)
        assertEquals("Burger", result.title)
    }

    @Test
    fun `deleteRecipe should call deleteById`() {
        recipeService.deleteRecipe(1)
        verify(recipeRepository, times(1)).deleteById(1)
    }
}
