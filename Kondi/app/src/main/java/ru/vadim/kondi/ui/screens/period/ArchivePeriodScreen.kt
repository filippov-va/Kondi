package ru.vadim.kondi.ui.screens.period

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import ru.vadim.kondi.ui.components.base.KondiMainTopBar
import ru.vadim.kondi.ui.viewmodels.GlobalViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WorksScreenArchive(
    globalViewModel: GlobalViewModel,
    navController: NavHostController,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            KondiMainTopBar(headerText = "Архив", openDrawer =  openDrawer,
            actions = {

            }
                )
        }
    ) {

    }
    
}