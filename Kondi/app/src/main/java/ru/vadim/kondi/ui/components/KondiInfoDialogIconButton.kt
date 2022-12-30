package ru.vadim.kondi.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.vadim.kondi.utils.Utils

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KondiInfoDialogIconButton(
    stringId: Int, visible: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
) {


    IconButton(onClick = { visible.value = true }) {
        Icon(
            imageVector = Icons.Filled.Help,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75F)
        )
    }


    val boxInteractionSource = remember { MutableInteractionSource() }
    val contentInteractionSource = remember { MutableInteractionSource() }

    if (!visible.value) return

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { /*visible.value = false*/ },
    ) {


        Box(
            Modifier
                .clickable(
                    interactionSource = boxInteractionSource,
                    indication = null,
                    onClick = { })

                .padding(2.dp)
                .widthIn(max = 400.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize()
                    .clickable(
                        indication = null,
                        interactionSource = contentInteractionSource,
                        onClick = {
                            //   visible.value = false
                        }),
                shape = MaterialTheme.shapes.medium,
                //    color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp,

                ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        //  .background(MaterialTheme.colorScheme.surface)
                        .padding(25.dp)
                ) {

                    Column(
                        Modifier.verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Help,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75F)
                        )

                        Spacer(modifier = Modifier.padding(5.dp))


                        Text(
                            text = stringResource(id = stringId),
                            textAlign = TextAlign.Justify,
                            modifier = Modifier

                                .fillMaxWidth(),

                            style = MaterialTheme.typography.bodyMedium,
                            //     color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.padding(15.dp))


                        TextButton(onClick = {


                            Utils().executeAfterDelay(500) { visible.value = false }


                        }) {

                            Text(
                                "Понятно", fontWeight = FontWeight.Bold,
                                // color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }

                    }

                }
            }
        }
    }


}
