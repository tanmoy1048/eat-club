package com.eatclubapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.eatclubapplication.presentation.navigation.AppNavigation
import com.eatclubapplication.ui.theme.EatclubApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EatclubApplicationTheme {
                AppNavigation()
            }
        }
    }
}
