package ru.vadim.kondi.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class KondiNavigationActions(navController: NavHostController) {


    val navigateToWorksByDatesScreen: () -> Unit = {
        navController.navigate(Screens.WorksByDatesScreen .route) {

            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToWorksInPartsScreen: () -> Unit = {
        navController.navigate(Screens.WorksInPartsScreen .route) {

            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToWorksArchiveScreen: () -> Unit = {
        navController.navigate(Screens.WorksArchiveScreen .route) {

            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }












    val navigateToProductsScreen: () -> Unit = {
        navController.navigate(Screens.ProductsScreen.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

}

