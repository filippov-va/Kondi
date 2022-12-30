package ru.vadim.kondi.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vadim.kondi.navigation.Screens
import ru.vadim.kondi.ui.theme.KondiTheme


@Composable
fun KondiAppNavRail(
    currentRoute: String,
    navigateToWorksByDatesScreen: () -> Unit,
    navigateToWorksInPartsScreen: () -> Unit,
    navigateToWorksArchiveScreen: () -> Unit,

    navigateToProductsMainScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(

        header = {

                 Text(text = "Kondi")
        },

        modifier = modifier
    ) {
       // Spacer(Modifier.weight(1f))
        NavigationRailItem(
            selected = currentRoute == Screens.WorksByDatesScreen .route,
            onClick = navigateToWorksArchiveScreen,
            icon = { Icon(Icons.Filled.Work, "h") },
        //    label = { Text("Выполненная\nработа") },
            alwaysShowLabel = false
        )

        Spacer(modifier = Modifier.padding(8.dp))

        NavigationRailItem(
            selected = currentRoute == Screens.ProductsScreen .route,
            onClick = navigateToProductsMainScreen,
            icon = { Icon(Icons.Filled.ListAlt, "") },
           // label = { Text("Список\nизделий") },
            alwaysShowLabel = false
        )
        Spacer(Modifier.weight(1f))
    }
}

@Preview("Drawer contents")
//@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppNavRail() {
    KondiTheme() {
        KondiAppNavRail(
            currentRoute = Screens.WorksByDatesScreen .route,
            navigateToWorksArchiveScreen = {},
            navigateToProductsMainScreen = {},
            navigateToWorksByDatesScreen = {},
            navigateToWorksInPartsScreen = {},
        )
    }
}