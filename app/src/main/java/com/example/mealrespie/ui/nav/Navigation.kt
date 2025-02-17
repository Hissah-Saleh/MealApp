package com.example.mealrespie.ui.nav

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.meal.ui.screens.search.SearchScreen
import com.example.mealrespie.ui.screens.details.DetailsScreen
import com.example.mealrespie.ui.screens.main_screen.MainScreen
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.MainScreen.route,
) {


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {


        composable(route = NavigationItem.MainScreen.route) {
            MainScreen(navigateToDetails = { id ->
                navController.navigate(NavigationItem.DetailsScreen(id).getPath())
            },
                navigateToSearch = {
                    navController.navigate(NavigationItem.SearchScreen.route)

                }
            )
        }

        composable(route = NavigationItem.SearchScreen.route) {
            SearchScreen(popBack = {
                navController.popBackStack()
            })
        }

        composable(route = NavigationItem.DetailsScreen().getPath(),
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            Timber.tag("#navigation").d(navController.currentDestination?.route)

            val mealId = backStackEntry.arguments?.getString("id")

            if (mealId != null)
                DetailsScreen(mealId,
                    navigateToSearch = {
                        navController.navigate(NavigationItem.SearchScreen.route)

                    },
                    popBack = {
                        navController.popBackStack()
                    }
                )
        }

    }

}