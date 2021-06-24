package com.sample.myapplication.model.network

data class RecepiesResponse(
    val count: Int,
    val recipes: List<Recipe>
)