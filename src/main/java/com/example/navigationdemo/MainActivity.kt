package com.example.navigationdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.navigationdemo.screens.Home
import com.example.navigationdemo.screens.Profile
import com.example.navigationdemo.screens.Welcome
import com.example.navigationdemo.ui.theme.NavigationDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(HomeScreen) // Создаем стек навигации с начальным экраном HomeScreen
    // Функция для навигации к новому экрану (добавляет ключ в стек)
    val onNavigation: (NavKey) -> Unit = {
        backStack.add(it) // Добавляем новый экран в конец стека
    }

    // Функция для очистки стека навигации (оставляет только первый экран)
    val onClearBackStack: () -> Unit = {
        while (backStack.size > 1) { // Пока в стеке больше 1 элемента
            backStack.removeLastOrNull() // Удаляем последний элемент
        }
    }

    NavDisplay(
        backStack = backStack, // Передаем текущий стек навигации
        onBack = { backStack.removeLastOrNull()
//                        while (backStack.size > 1) {
//                            backStack.removeLastOrNull()
//                        }
            // Удаляет все экраны из стека навигации, кроме самого первого (домашнего).
        },
        entryProvider = entryProvider { // Провайдер для определения экранов
            entry<HomeScreen> { // Регистрируем экран HomeScreen
                Home(onNavigation)
            }
            entry<WelcomeScreen>( // Регистрируем экран WelcomeScreen
                metadata = mapOf("extraDataKey" to "extraDataValue")
            ) { key -> // Получаем ключ навигации (WelcomeScreen)
                Welcome(onNavigation, key.name) // Отображаем компонент Welcome с именем
            }
            entry<ProfileScreen> { // Регистрируем экран ProfileScreen
                Profile(onClearBackStack) // Отображаем компонент Profile
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    NavigationDemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MainScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}