package ru.vadim.kondi.ui.screens.productscreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.vadim.kondi.domain.product.ProductItem
import ru.vadim.kondi.ui.components.base.KondiAddEditTopBar
import ru.vadim.kondi.ui.components.KondiInputTextField
import ru.vadim.kondi.ui.components.base.KondiInfoDialogIconButton
import ru.vadim.kondi.ui.viewmodels.GlobalViewModel
import ru.vadim.kondi.utils.ScreenMode
import ru.vadim.kondi.R

fun onDeleteProduct(productItem: ProductItem, globalViewModel: GlobalViewModel) {
    globalViewModel.deleteProduct(productItem)
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductAddEditScreen(
    globalViewModel: GlobalViewModel, openDrawer: () -> Unit, navController: NavHostController
) {

    val productScreenMode by globalViewModel.productScreenMode.observeAsState()
    val titleText = remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    val idState = remember { mutableStateOf("") }
    val itemState = remember { mutableStateOf("") }
    val kilogramState = remember { mutableStateOf("") }
    val gramState = remember { mutableStateOf("") }
    val rublesState = remember { mutableStateOf("") }
    val kopeckState = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }




    val onSaveProduct = {


        if (globalViewModel.validateInput(
                itemState.value,
                kilogramState.value,
                gramState.value,
                rublesState.value,
                kopeckState.value
            )
        ) {

            when (productScreenMode) {

                ScreenMode.Addition -> {
                    globalViewModel.addProduct(
                        itemState.value,
                        kilogramState.value,
                        gramState.value,
                        rublesState.value,
                        kopeckState.value
                    )



                }

                ScreenMode.Editing -> {
                    globalViewModel.updateProduct(
                        itemState.value,
                        kilogramState.value,
                        gramState.value,
                        rublesState.value,
                        kopeckState.value,
                        inputId = idState.value.toInt()
                    )



                }

                else -> {}
            }

            navController.popBackStack()

        }else{

            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar("Не все данные заполнены!\n")

            }


        }

    }



    LaunchedEffect(null) {

        when (productScreenMode) {

            ScreenMode.Addition -> {
                titleText.value = "Добавление изделия"
            }

            ScreenMode.Editing -> {
                titleText.value = "Редактирование изделия"

                val selectedProduct = globalViewModel.selectedProduct

                if (selectedProduct != null) {

                    itemState.value = selectedProduct.value?.item_name ?: "null"
                    kilogramState.value = selectedProduct.value?.kilogram?.toString() ?: "null"
                    idState.value = selectedProduct.value?.id?.toString() ?: ""
                    gramState.value = selectedProduct.value?.gram?.toString() ?: "null"
                    rublesState.value = selectedProduct.value?.rubles?.toString() ?: "null"
                    kopeckState.value = selectedProduct.value?.kopeck?.toString() ?: "null"
                }
            }

            else -> {}
        }


    }

    DisposableEffect(null) {

        this.onDispose {
            globalViewModel.setProductScreenMode(ScreenMode.Reading)

        }


    }






    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,

        topBar = {


            KondiAddEditTopBar(


                headerText = titleText.value,

                actions = {

                    when (productScreenMode) {

                        ScreenMode.Addition -> {

                            IconButton(onClick = { onSaveProduct() }) {
                                Icon(imageVector = Icons.Filled.Save,
                                    contentDescription = "",)
                            }

                            KondiInfoDialogIconButton(stringId = R.string.ProductsScreenModeAddInfo)

                        }

                        ScreenMode.Editing -> {

                            IconButton(
                                onClick = {
                                    globalViewModel.selectedProduct?.value?.let {
                                        onDeleteProduct(it, globalViewModel)
                                        navController.popBackStack()
                                    }
                                }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "",

                                )
                            }

                            IconButton(
                                onClick = {
                                    onSaveProduct()
                                }) {
                                Icon(
                                    imageVector = Icons.Filled.Save,
                                    contentDescription = "",

                                )
                            }



                            KondiInfoDialogIconButton(stringId = R.string.ProductsScreenModeEditInfo)



                        }

                        else -> {}
                    }



                },
                navController = navController, navigationIconClick = { }

            )


        },

        content = {


            Column(
                modifier = Modifier

                    .fillMaxSize()
                    .padding(10.dp)
            ) {


                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 1.dp, bottom = 0.dp),
                    text = "Нужно ввести данные для изделия (коробки)",
                    style = androidx.compose.material3.MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.5f
                    ),

                    )


                Spacer(modifier = Modifier.padding(15.dp))



                Column() {
                    KondiInputTextField(
                        label = "Наименование изделия",
                        state = itemState,
                        onValueChange = { itemState.value = it },
                        focusRequester = focusRequester,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
                        ),
                        trailingIcon = Icons.Outlined.Close,
                        modifier = Modifier
                    )




                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp, bottom = 0.dp),
                        text = "Вес готового изделия (коробки)",
                        style = androidx.compose.material3.MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.5f
                        ),

                        )
                }

                Spacer(modifier = Modifier.size(2.dp))

                Row(
                    horizontalArrangement = Arrangement.Center
                ) {

                    KondiInputTextField(
                        label = "Килограмм",
                        state = kilogramState,
                        onValueChange = { kilogramState.value = it },
                        focusRequester = focusRequester,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                        ),
                        trailingIcon = Icons.Outlined.Close,
                        modifier = Modifier.weight(1f)
                    )


                    Spacer(modifier = Modifier.size(10.dp))


                    KondiInputTextField(
                        label = "Грамм",
                        state = gramState,
                        onValueChange = { gramState.value = it },
                        focusRequester = focusRequester,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                        ),
                        trailingIcon = Icons.Outlined.Close,
                        modifier = Modifier.weight(1f)
                    )


                }

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 4.dp),
                    text = "Стоимость изготовки одного килограмма изделия",
                    style = androidx.compose.material3.MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.5f
                    ),

                    )


                Row(
                    horizontalArrangement = Arrangement.Center
                ) {

                    KondiInputTextField(
                        label = "Рублей",
                        state = rublesState,
                        onValueChange = { rublesState.value = it },
                        focusRequester = focusRequester,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                        ),
                        trailingIcon = Icons.Outlined.Close,
                        modifier = Modifier.weight(1f)
                    )


                    Spacer(modifier = Modifier.size(10.dp))


                    KondiInputTextField(
                        label = "Копеек",
                        state = kopeckState,
                        onValueChange = { kopeckState.value = it },
                        focusRequester = focusRequester,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                        ),
                        trailingIcon = Icons.Outlined.Close,
                        modifier = Modifier.weight(1f)
                    )


                }


            }


        },

        backgroundColor = MaterialTheme.colorScheme.background


        )




}

