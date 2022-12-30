package ru.vadim.kondi.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PartyMode
import androidx.compose.material.icons.filled.Repartition
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Help
import androidx.compose.material.icons.rounded.ListAlt
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String, val icon: ImageVector? = null, val title: String = "") {

    object SplashScreen : Screens(route = "splash_screen")
    object OnBoardingScreen : Screens(route = "on_boarding_screen")
    object OptionsScreen : Screens(route = "options_screen", icon = Icons.Rounded.Settings, "Настройки программы")


    object WorksByDatesScreen : Screens(route = "works_by_dates", icon = Icons.Filled.DateRange, "По датам")
    object WorksInPartsScreen : Screens(route = "works_in_parts", icon = Icons.Filled.Repartition, "По частям")
    object WorksArchiveScreen : Screens(route = "works_archive", icon = Icons.Filled.Archive, "Архив")

    object ProductsScreen : Screens(route = "product_screen", icon = Icons.Rounded.ListAlt, "Список изделий")
    object ProductAddEditScreen : Screens(route = "product_add_edit_screen", icon = Icons.Rounded.Edit, "Редактор изделия")





}


