package com.example.krashaksetu.components

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.krashaksetu.Screen
import com.example.krashaksetu.viewModels.LanguageViewModel
import com.example.krashaksetu.viewModels.LoginViewModel
import com.example.krashaksetu.viewModels.ProductViewModel


@Composable
fun KrashakSetuApp(viewModel: LanguageViewModel, localizedContext: Context) {
    val navController = rememberNavController()
    val productViewModel: ProductViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()


    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            AnimatedSplashScreen(navController = navController)
        }
        composable(Screen.LanguageSelection.route) {
            CompositionLocalProvider(LocalContext provides localizedContext) {
                LanguageSelectionScreen(
                    navController = navController,
                    languageViewModel = viewModel,
                    context = localizedContext
                )
            }

        }
        composable(Screen.Login.route) {
            CompositionLocalProvider(LocalContext provides localizedContext) {
                LoginScreen(
                    onNavigateToNextScreen = { navController.navigate(Screen.Home.route) },
                    languageViewModel = viewModel,
                    context = localizedContext,
                    loginViewModel = loginViewModel
                )
            }

        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.ProductListing.route) {
            ProductListingScreen(
                productViewModel = productViewModel,
                onAddProductClick = { navController.navigate(Screen.AddProductForm.route) },
                onEditProductClick = { navController.navigate(Screen.AddProductForm.route) }
            )
        }
        composable(Screen.AddProductForm.route) {
            AddProductScreen(productViewModel = productViewModel,
                onSaveProduct = { navController.popBackStack() },
                localizedContext = localizedContext
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }
}

@Composable
fun GradientBackgroundBrush(
    isVerticalGradient: Boolean,
): Brush {
    val gradientColorList = listOf(
        Color(0xFF2E7D32),
        Color(0xFFFFFFFF)
    )
    val endOffset = if(isVerticalGradient) {
        Offset(0f, Float.POSITIVE_INFINITY)
    }else {
        Offset(Float.POSITIVE_INFINITY, 0f)
    }

    return Brush.linearGradient(
        colors = gradientColorList,
        start = Offset.Zero,
        end = endOffset
    )

}