package ru.vadim.kondi.ui


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.vadim.kondi.navigation.KondiNavigationGraph
import ru.vadim.kondi.navigation.KondiNavigationActions
import ru.vadim.kondi.ui.components.KondiAppDrawer
import ru.vadim.kondi.ui.components.KondiAppNavRail
import ru.vadim.kondi.navigation.Screens
import ru.vadim.kondi.ui.theme.KondiTheme
import ru.vadim.kondi.ui.viewmodels.GlobalViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            BackPressHandler(onBackPressed = {


            }, enabled = true)

            KondiTheme(dynamicColor = false) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    KondiApp(widthSizeClass)
                }
            }
        }
    }
}


@SuppressLint(
    "UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter",
    "SuspiciousIndentation"
)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun KondiApp(
    widthSizeClass: WindowWidthSizeClass
) {
    val globalViewModel = hiltViewModel<GlobalViewModel>()
    val navController = rememberNavController()

    val navigationActions = remember(navController) {
        KondiNavigationActions(navController)
    }
    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: Screens.WorksByDatesScreen .route

    val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
    val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)





    ModalNavigationDrawer(
        drawerContent = {
            KondiAppDrawer(
                currentRoute = currentRoute,
                navigateToByDates = navigationActions.navigateToWorksByDatesScreen,
                navigateToInParts = navigationActions.navigateToWorksInPartsScreen,
                navigateToInterests = navigationActions.navigateToProductsScreen,
                navigateToArchive = navigationActions.navigateToWorksArchiveScreen,
                closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } },
                globalViewModel = globalViewModel
            )
        },
        drawerState = sizeAwareDrawerState,
        // Only enable opening the drawer via gestures if the screen is not expanded
        gesturesEnabled = !isExpandedScreen,
        scrimColor = MaterialTheme.colorScheme.primary,

        ) {
        Row {
            if (isExpandedScreen) {

                KondiAppNavRail(
                    currentRoute = currentRoute,

                    navigateToWorksByDatesScreen = navigationActions.navigateToWorksByDatesScreen,
                    navigateToWorksInPartsScreen = navigationActions.navigateToWorksInPartsScreen,
                    navigateToWorksArchiveScreen = navigationActions.navigateToWorksArchiveScreen,

                    navigateToProductsMainScreen = navigationActions.navigateToProductsScreen,

                    )
            }


            KondiNavigationGraph(
                isExpandedScreen = isExpandedScreen,
                navController = navController,
                openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },
                globalViewModel = globalViewModel
            )


        }
    }


}


@Composable
fun BackPressHandler(onBackPressed: () -> Unit, enabled: Boolean = true) {
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    DisposableEffect(dispatcher) { // утилизировать / перезапустить, если диспетчер изменится
        dispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove() // избегайте утечек!
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

/**
 * Determine the content padding to apply to the different screens of the app
 */
@Composable
fun rememberContentPaddingForScreen(
    additionalTop: Dp = 0.dp,
    excludeTop: Boolean = false
) =
    WindowInsets.systemBars
        .only(if (excludeTop) WindowInsetsSides.Bottom else WindowInsetsSides.Vertical)
        .add(WindowInsets(top = additionalTop))
        .asPaddingValues()