package ru.vadim.kondi.ui.components.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun KondiElevatedCard(
    titleText: String,
    onCardClick: () -> Unit,
    onActionClick: () -> Unit?,
    modifier: Modifier = Modifier
        .padding(6.dp)
        .fillMaxWidth(),
    content: @Composable (() -> Unit)
) {

    ElevatedCard(
        modifier = modifier, onClick = { onCardClick.invoke() }) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                horizontalArrangement = Arrangement.Start,

                ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1F, fill = true)
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),

                        text = titleText,
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )

                    )
                }

                Spacer(modifier = Modifier.size(5.dp))

                Box(
                    modifier = Modifier.sizeIn(maxWidth = 20.dp, maxHeight = 20.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    IconButton(onClick = { onActionClick.invoke() }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "",
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.7f
                            )
                        )
                    }
                }


            }


            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .wrapContentSize(align = Alignment.Center)
            ) {
                content.invoke()
            }


        }


    }


}


@Composable
fun KondiElevatedCard2(
    titleText: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
        .padding(1.dp)
        .fillMaxWidth(),
    content: @Composable (() -> Unit)
) {

    ElevatedCard(
        modifier = modifier, onClick = { onCardClick.invoke() }) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                horizontalArrangement = Arrangement.Start,

                ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),

                    text = titleText,
                    style = androidx.compose.material3.MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Medium
                    )

                )

            }


            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .wrapContentSize(align = Alignment.Center)
            ) {
                content.invoke()
            }


        }


    }


}


@Composable
fun KondiElevatedCard3(
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
        .padding(1.dp)
        .fillMaxWidth(),
    content: @Composable (() -> Unit)
) {

    ElevatedCard(
        modifier = modifier, onClick = { onCardClick.invoke() }) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {



            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .wrapContentSize(align = Alignment.Center)
            ) {
                content.invoke()
            }


        }


    }


}

