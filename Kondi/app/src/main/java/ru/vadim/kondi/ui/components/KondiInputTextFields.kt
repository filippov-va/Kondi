package ru.vadim.kondi.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*


import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.MyLocation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.vadim.kondi.utils.Utils


@Composable
fun KondiDateInputTextFields(
    state: MutableState<String>,
    label: String,
    action: () ->  Unit
) {


    val visibled  = rememberSaveable { mutableStateOf(false) }

    KondiSetDateDialog(state, visibled)

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .sizeIn(maxWidth = 150.dp) ,
        value =   if (state.value != "") state.value else "не выбрано",
        onValueChange = { state.value = it },
        // textStyle = MaterialTheme.typography.titleMedium,
        enabled = true,
        label = {
            Text(
                text = label,

            )
        },

        leadingIcon = {
            IconButton(onClick = {
              //  action.invoke()
                visibled.value = true

            }) {
                Icon(
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = null,

                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next
        ),
        trailingIcon = {
            IconButton(onClick = {

                state.value = Utils().getCurrentDateInFormat()


            }) {
                Icon(
                    imageVector = Icons.Outlined.MyLocation,
                    contentDescription = null,

                )
            }
        },
        readOnly = true,
        shape = RoundedCornerShape(15.dp),
    )

}










@Composable
fun KondiOutlinedTextField(
    label: String,
    state: MutableState<String>,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    keyboardOptions: KeyboardOptions,
    trailingIcon: ImageVector,
    modifier: Modifier

) {



        OutlinedTextField(
            label = { Text(text = label) },

            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .sizeIn(maxWidth = 150.dp) ,
            value = state.value,
            keyboardOptions = keyboardOptions,

            onValueChange = {
                onValueChange.invoke(it)
            },
            shape = RoundedCornerShape(15.dp),
            singleLine = true,

            trailingIcon = {
                if (state.value.isNotEmpty()) {
                    IconButton(onClick = { state.value = ""  }) {
                        Icon(
                            imageVector = trailingIcon,
                            contentDescription = null, tint = MaterialTheme.colorScheme.onBackground

                        )
                    }
                }
            }

        )





    }





