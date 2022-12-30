package ru.vadim.kondi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vadim.kondi.ui.components.HelpScreen
import ru.vadim.kondi.ui.mainscreens.OptionsScreen
import ru.vadim.kondi.ui.mainscreens.ProductScreen
import ru.vadim.kondi.ui.productscreens.ProductAddEditScreen
import ru.vadim.kondi.ui.viewmodels.GlobalViewModel
import ru.vadim.kondi.ui.worksscreens.WorksScreenArchive
import ru.vadim.kondi.ui.worksscreens.WorksScreenByDates
import ru.vadim.kondi.ui.worksscreens.WorksScreenInParts

@Composable
fun KondiNavigationGraph(
    isExpandedScreen: Boolean,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = Screens.WorksByDatesScreen.route,
    globalViewModel: GlobalViewModel


) {


    NavHost(
        navController = navController, startDestination = startDestination, modifier = Modifier
    ) {

        composable(Screens.WorksByDatesScreen.route) {

            WorksScreenByDates(
                navController = navController,
                globalViewModel = globalViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }

        composable(Screens.WorksInPartsScreen .route) {

            WorksScreenInParts(
                navController = navController,
                globalViewModel = globalViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }

        composable(Screens.WorksArchiveScreen .route) {

            WorksScreenArchive(
                navController = navController,
                globalViewModel = globalViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }


        composable(Screens.OptionsScreen.route) {

            OptionsScreen(navController = navController)
        }




        composable(Screens.ProductsScreen.route) {

            ProductScreen(
                navController = navController,
                globalViewModel = globalViewModel,
                openDrawer = openDrawer
            )
        }

        composable(Screens.ProductAddEditScreen.route) {


            ProductAddEditScreen(globalViewModel = globalViewModel)
        }

    }


}





