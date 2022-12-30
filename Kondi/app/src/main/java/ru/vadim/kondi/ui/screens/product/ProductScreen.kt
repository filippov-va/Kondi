package ru.vadim.kondi.ui.screens.productscreens


import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import ru.vadim.kondi.R
import ru.vadim.kondi.domain.product.ProductItem
import ru.vadim.kondi.navigation.Screens
import ru.vadim.kondi.ui.components.KondiAddingWorkDialog
import ru.vadim.kondi.ui.components.KondiEmptyListIndicator
import ru.vadim.kondi.ui.components.base.KondiInfoDialogIconButton
import ru.vadim.kondi.ui.components.base.KondiMainTopBar
import ru.vadim.kondi.ui.viewmodels.GlobalViewModel
import ru.vadim.kondi.utils.ScreenMode
import ru.vadim.kondi.utils.Utils


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(
    navController: NavHostController, globalViewModel: GlobalViewModel, openDrawer: () -> Unit
) {

    //Получаем список всех изделий
    val listAllProducts = globalViewModel.allProducts.observeAsState(mutableListOf())

    //Стейт видимости диалога добавления записи об изготовленном изделии
    val visibleAddWorkDialog = remember { mutableStateOf(false) }



    if (visibleAddWorkDialog.value) {
        KondiAddingWorkDialog(
            globalViewModel = globalViewModel,
            visible = visibleAddWorkDialog,
        )
    }



    androidx.compose.material.Scaffold(

        backgroundColor = MaterialTheme.colorScheme.background,

        topBar = {

            KondiMainTopBar(
                headerText = stringResource(id = R.string.products_screen),
                openDrawer = openDrawer,
                actions = {

                    IconButton(onClick = {

                        globalViewModel.setProductScreenMode(ScreenMode.Addition)

                        navController.navigate(Screens.ProductAddEditScreen.route)

                    }) { Icon(imageVector = Icons.Filled.Add, contentDescription = "") }

                    KondiInfoDialogIconButton(
                        R.string.ProductsScreenInfo
                    )

                }) },

        content = {


            if (listAllProducts.value.isNotEmpty()) {


                ProductsGrid(
                    data = listAllProducts.value,

                    onCardClick = {
                        globalViewModel.selectProduct(it.id)

                        Utils().executeAfterDelay {
                            visibleAddWorkDialog.value = true
                        }


                    },

                    onActionClick = {

                        EditProduct(globalViewModel, it, navController)


                    },

                    )


            } else {
                KondiEmptyListIndicator(text = "Список пуст")
            }


        },


        )


}


private fun EditProduct(
    globalViewModel: GlobalViewModel, productItem: ProductItem, navController: NavHostController
) {

    globalViewModel.setProductScreenMode(ScreenMode.Editing)

    globalViewModel.selectProduct(productItem.id)

    Utils().executeAfterDelay {
        navController.navigate(Screens.ProductAddEditScreen.route)
    }
}


