package ru.vadim.kondi.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vadim.kondi.navigation.Screens
import ru.vadim.kondi.ui.theme.KondiTheme
import ru.vadim.kondi.ui.viewmodels.GlobalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KondiAppDrawer(
    currentRoute: String,
    navigateToByDates: () -> Unit,
    navigateToInParts: () -> Unit,
    navigateToArchive: () -> Unit,
    navigateToInterests: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    globalViewModel: GlobalViewModel?
) {


    val mode = globalViewModel?.productMode?.observeAsState()


    ModalDrawerSheet(modifier) {

        KondiAppLogo(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp)
        )

        Column(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 1.dp)


        ) {

            Text(
                text = "Выполненная работа",
                style = MaterialTheme.typography.headlineSmall,
                )
            Spacer(Modifier.padding(2.dp))

            NavigationDrawerItem(label = { Text(
                Screens.WorksByDatesScreen.title,
            ) },
                icon = { Icon(Screens.WorksByDatesScreen.icon!!, null) },
                selected = currentRoute == Screens.WorksByDatesScreen.route,
                onClick = { navigateToByDates(); closeDrawer() },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )

            NavigationDrawerItem(label = { Text(Screens.WorksInPartsScreen.title) },
                icon = { Icon(Screens.WorksInPartsScreen.icon!!, null) },
                selected = currentRoute == Screens.WorksInPartsScreen.route,
                onClick = { navigateToInParts(); closeDrawer() },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )

            NavigationDrawerItem(label = { Text(Screens.WorksArchiveScreen.title) },
                icon = { Icon(Screens.WorksArchiveScreen.icon!!, null) },
                selected = currentRoute == Screens.WorksArchiveScreen.route,
                onClick = { navigateToArchive(); closeDrawer() },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )

        }

        Spacer(Modifier.padding(10.dp))

        Column(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 1.dp)


        ) {

            Text(
                text = "Изделия",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(Modifier.padding(2.dp))

            if (mode != null) {

                if (mode.value == true) {

                    NavigationDrawerItem(label = { Text(Screens.ProductAddEditScreen.title) },
                        icon = { Icon(Screens.ProductAddEditScreen.icon!!, null) },
                        selected = currentRoute == Screens.ProductAddEditScreen.route,
                        onClick = { navigateToInterests(); closeDrawer() },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                } else {
                    NavigationDrawerItem(label = { Text(Screens.ProductsScreen.title) },
                        icon = { Icon(Screens.ProductsScreen.icon!!, null) },
                        selected = currentRoute == Screens.ProductsScreen.route,
                        onClick = { navigateToInterests(); closeDrawer() },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }

        }












    }
}


@Composable
private fun KondiAppLogo(modifier: Modifier = Modifier) {

    Column(modifier = modifier) {
        Text(
            text = "Kondi",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(Modifier.padding(5.dp))

    }
}

@Preview("Drawer contents")
//@Preview("Drawer contents (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    KondiTheme {
        KondiAppDrawer(currentRoute = Screens.WorksByDatesScreen.route,
            navigateToByDates = {},
            navigateToInterests = {},
            closeDrawer = { },
            globalViewModel = null,
            navigateToArchive = {},
            navigateToInParts = {})
    }
}