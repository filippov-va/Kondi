package ru.vadim.kondi.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController

@Composable
fun KondiMainTopBar(
    headerText: String,
    openDrawer: () -> Unit,
    actions: @Composable RowScope.() -> Unit,
) {


    TopAppBar(
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                )
            }
        },
        title = {

            KondiHeaderText(headerText)

        },
        actions = actions,

        )


}

@Composable
fun KondiAddEditTopBar(
    headerText: String,
    actions: @Composable RowScope.() -> Unit,
    navController: NavHostController,
    navigationIconClick: () -> Unit
) {


    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                navigationIconClick.invoke()
                navController.popBackStack()
            }) {

                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },

        title = {
            KondiHeaderText(headerText = headerText)
        }, actions = actions
    )


}

