package com.example.reciepebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.reciepebox.navigation.AppNavigation
import com.example.reciepebox.ui.theme.ReciepeboxTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ReciepeboxTheme {
                AppNavigation()
            }
        }
    }
}