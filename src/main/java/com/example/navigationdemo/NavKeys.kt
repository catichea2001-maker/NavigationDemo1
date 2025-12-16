package com.example.navigationdemo

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable // Аннотация позволяет сериализовать объект для передачи между экранами
data object HomeScreen : NavKey
@Serializable
data class WelcomeScreen(val name: String) : NavKey
@Serializable
data object ProfileScreen : NavKey