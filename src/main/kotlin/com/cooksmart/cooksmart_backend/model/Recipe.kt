package com.cooksmart.model

import jakarta.persistence.*

@Entity
@Table(name = "recipes")
data class Recipe(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val ingredients: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val instructions: String,

    val calories: Int? = null,

    val country: String? = null
)
