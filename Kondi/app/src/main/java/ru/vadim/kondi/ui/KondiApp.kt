package ru.vadim.kondi.ui

import android.app.Activity
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.vadim.kondi.navigation.KondiNavigationActions
import ru.vadim.kondi.navigation.KondiNavigationGraph
import ru.vadim.kondi.navigation.Screens
import ru.vadim.kondi.ui.components.KondiAppDrawer
import ru.vadim.kondi.ui.components.KondiAppNavRail
import ru.vadim.kondi.ui.viewmodel.GlobalViewModel
import ru.vadim.kondi.utils.ScreenMode


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KondiApp(
    widthSizeClass: WindowWidthSizeClass
) {

    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screens.PeriodScreen.route
    val navigationActions = remember(navController) { KondiNavigationActions(navController) }

    val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
    val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

    val globalViewModel = hiltViewModel<GlobalViewModel>()

    val productScreenMode = globalViewModel.productScreenMode.observeAsState(ScreenMode.Reading)
    val worksInPartsScreenMode = globalViewModel.periodScreenMode.observeAsState(ScreenMode.Reading)


    globalViewModel.onExit.observeAsState().let {
        if (it.value == true) (LocalContext.current as? Activity)?.finishAndRemoveTask()
    }




    ModalNavigationDrawer(
        drawerContent = {
            KondiAppDrawer(
                currentRoute = currentRoute,
                navigateToInParts = navigationActions.navigateToPeriodScreen,
                navigateToInterests = navigationActions.navigateToProductScreen,
                navigateToArchive = navigationActions.navigateToArchivePeriodScreen,
                closeDrawer = { scope.launch { sizeAwareDrawerState.close() } },
                globalViewModel = globalViewModel
            )
        },
        drawerState = sizeAwareDrawerState,
        gesturesEnabled = !isExpandedScreen && productScreenMode.value == ScreenMode.Reading && worksInPartsScreenMode.value == ScreenMode.Reading,
        scrimColor = MaterialTheme.colorScheme.background
    ) {
        Row {
            if (isExpandedScreen) {

                KondiAppNavRail(
                    currentRoute = currentRoute,



                    navigateToProductsMainScreen = navigationActions.navigateToProductScreen,

                    )
            }

            KondiNavigationGraph(
                isExpandedScreen = isExpandedScreen,
                navController = navController,
                openDrawer = { scope.launch { sizeAwareDrawerState.open() } },
                globalViewModel = globalViewModel
            )


        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        // If we want to allow showing the drawer, we use a real, remembered drawer
        // state defined above
        drawerState
    } else {
        // If we don't want to allow the drawer to be shown, we provide a drawer state
        // that is locked closed. This is intentionally not remembered, because we
        // don't want to keep track of any changes and always keep it closed
        DrawerState(DrawerValue.Closed)
    }
}

