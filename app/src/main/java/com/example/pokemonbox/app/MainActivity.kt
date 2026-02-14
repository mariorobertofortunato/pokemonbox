package com.example.pokemonbox.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pokemonbox.app.ui.screens.MainScreen
import com.example.pokemonbox.app.ui.theme.PokemonBoxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PokemonBoxTheme {
                MainScreen()
            }
        }
    }
}