package ru.vadim.kondi.ui.components


import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.vadim.kondi.R
import ru.vadim.kondi.domain.work.WorkItem
import ru.vadim.kondi.ui.viewmodel.GlobalViewModel
import ru.vadim.kondi.utils.Utils


/**
 * Диалог добавления записи об озготовлении изделия
 * @param visible state видимости диалога.
 * @param globalViewModel вью модель
 */

@SuppressLint("SuspiciousIndentation")
@Composable
internal fun KondiAddingWorkDialog(
    visible: MutableState<Boolean>,
    globalViewModel: GlobalViewModel,

    ) {


    if (!visible.value) return


    val boxInteractionSource = remember { MutableInteractionSource() }
    val contentInteractionSource = remember { MutableInteractionSource() }

    val selectedProduct = globalViewModel.selectedProduct.observeAsState()

    val dateState = remember {
        mutableStateOf(Utils().getCurrentDateInFormat())
    }

    var volumeProductsState by remember { mutableStateOf("") }

    val periodIdState = remember {
        mutableStateOf(0)
    }




    Dialog(onDismissRequest = {}) {


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clickable(interactionSource = boxInteractionSource,
                    indication = null,
                    onClick = { visible.value = false })
                .wrapContentSize()
        ) {


            Surface(modifier = Modifier
                .animateContentSize()
                .wrapContentSize()
                .clickable(
                    indication = null,
                    interactionSource = contentInteractionSource,
                    onClick = { }), shape = MaterialTheme.shapes.large,

                content = {


                    Row(
                        horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()
                    ) {
                        KondiInfoDialogIconButton(
                            stringId = R.string.AddingWorkDialog
                        )


                    }

                    Column(
                        modifier = Modifier
                            .wrapContentSize()

                            .padding(start = 25.dp, end = 25.dp, top = 1.dp, bottom = 10.dp)
                    ) {


                        Spacer(modifier = Modifier.padding(25.dp))

                        Text(
                            text = "Добавление записи\nоб изготовлении изделия",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.headlineSmall,
                        )

                        Spacer(modifier = Modifier.padding(10.dp))

                        Column(
                            modifier = Modifier
                                .padding(5.dp)
                                .wrapContentSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {


                            Badge(modifier = Modifier.fillMaxWidth(), content = {

                                Text(
                                    modifier = Modifier.padding(start = 3.dp, end = 3.dp),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium,
                                    text = selectedProduct.value?.item_name ?: "null",
                                )
                            })

                            Spacer(modifier = Modifier.padding(10.dp))

                            KondiInputDateField(
                                dateState,
                                "Дата",
                                trailingIcon = Icons.Filled.LocationSearching,
                                trailingAction = {

                                    it.value = Utils().getCurrentDateInFormat()

                                },
                                title = "Выбери дату изготовления изделия"
                            )



                            OutlinedTextField(
                                value = volumeProductsState,

                                onValueChange = { volumeProductsState = it },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.NumberPassword,
                                    imeAction = ImeAction.Done
                                ),
                                label = {
                                    Text(
                                        text = "Количество изделий (коробок)",

                                        )
                                },


                                singleLine = true,
                                readOnly = false,
                                shape = RoundedCornerShape(15.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .sizeIn(maxWidth = 150.dp)

                            )



                            SelectPeriod(periodIdState, globalViewModel = globalViewModel)





                            Row(
                                Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceAround
                            ) {

                                TextButton(onClick = {

                                    visible.value = false

                                }) {

                                    Text(
                                        "Отмена", fontWeight = FontWeight.Bold,
                                        // color = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                                    )
                                }
                                TextButton(

                                    enabled = volumeProductsState.isNotEmpty() && periodIdState.value != 0,

                                    onClick = {


                                        val it = globalViewModel.selectedProduct.value

                                        if (it != null) {

                                            val sumResult: Double =
                                                0.0 + volumeProductsState.toInt() * (it.rubles + (it.kopeck * 0.01)) * (it.kilogram + (it.gram * 0.001))


                                            val workItem = WorkItem(
                                                Utils().convertDateFromStringToLong(dateState.value),
                                                it.item_name,
                                                volumeProductsState.toInt(),
                                                mass = 0.0 + (it.kilogram + it.gram) * volumeProductsState.toInt(),
                                                sum = sumResult,
                                            )






                                            globalViewModel.addWorkItemToPeriodItem(
                                                periodIdState.value, workItem
                                            )







                                            visible.value = false

                                        }


                                    }) {
                                    Text(

                                        "Добавить", fontWeight = FontWeight.Bold,


                                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                                    )
                                }
                            }


                        }


                    }


                })
        }
    }


}


