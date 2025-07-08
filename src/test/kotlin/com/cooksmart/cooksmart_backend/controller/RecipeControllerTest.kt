package com.cooksmart.cooksmart_backend.controller

import com.cooksmart.controller.RecipeController
import com.cooksmart.cooksmart_backend.service.RecipeApiService
import com.cooksmart.model.Recipe
import com.cooksmart.model.api.RecipeResponse
import com.cooksmart.service.RecipeService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RecipeController::class)
class RecipeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var recipeService: RecipeService

    @MockBean
    private lateinit var recipeApiService: RecipeApiService

    @Test
    fun `GET all recipes`() {
        val recipes = listOf(Recipe(1, "Pizza", "Cheese", "Bake"))
        given(recipeService.getAllRecipes()).willReturn(recipes)

        mockMvc.perform(get("/api/recipes"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].title").value("Pizza"))
    }

    @Test
    fun `GET recipe by id`() {
        val recipe = Recipe(1, "Pizza", "Cheese", "Bake")
        given(recipeService.getRecipeById(1)).willReturn(recipe)

        mockMvc.perform(get("/api/recipes/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("Pizza"))
    }

    @Test
    fun `DELETE recipe`() {
        mockMvc.perform(delete("/api/recipes/1"))
            .andExpect(status().isOk)
    }

    @Test
    fun `GET external recipes`() {
        runBlocking {
            val response = listOf(
                RecipeResponse(
                    id = 1,
                    title = "Pizza",
                    image = "image.jpg",
                    imageType = "jpg",
                    likes = 100,
                    missedIngredientCount = 0,
                    usedIngredientCount = 0,
                    missedIngredients = emptyList(),
                    usedIngredients = emptyList()
                )
            )
            Mockito.`when`(recipeApiService.getRecipesByIngredients("tomato"))
                .thenReturn(response)



            mockMvc.perform(get("/api/recipes/external?ingredients=tomato"))
                .andExpect(status().isOk)
        }
    }
}
