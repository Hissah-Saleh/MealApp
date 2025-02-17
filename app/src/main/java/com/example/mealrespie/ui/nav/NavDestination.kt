package com.example.mealrespie.ui.nav

enum class NavDestination {
    MAIN,
    DETAILS,
    SEARCH,
}

sealed class NavigationItem(val route: String) {
    object MainScreen : NavigationItem(route= NavDestination.MAIN.name)
    class DetailsScreen(val id :String="{id}") : NavigationItem(NavDestination.DETAILS.name){
        fun getPath()= NavDestination.DETAILS.name+ "/$id"

    }
    object SearchScreen : NavigationItem(route= NavDestination.DETAILS.name)

}