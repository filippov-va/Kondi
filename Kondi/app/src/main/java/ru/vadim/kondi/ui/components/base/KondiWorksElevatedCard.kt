package ru.vadim.kondi.ui.components.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vadim.kondi.domain.work.WorkItem
import ru.vadim.kondi.utils.Utils

@Composable
fun KondiWorksElevatedCard(
    pair: Pair<Long, List<WorkItem>>

) {
    val expanded = rememberSaveable {
        mutableStateOf(false)
    }





    KondiElevatedCard2(

        titleText = Utils().convertDateFromLongToString(pair.first)!!,
        onCardClick = { expanded.value = !expanded.value }) {

        var sumR = 0.0




        expanded.value.let {

            when (!it) {

                true -> {


                    pair.second.forEachIndexed { _, workItem ->

                        if (workItem.date == pair.first) sumR += workItem.sum


                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Badge(modifier = Modifier.padding(all = 0.dp),
                                content = {


                                    Text(
                                        style = MaterialTheme.typography.titleSmall,
                                        text = Utils().fromDoubleToStringMoney(sumR)

                                    )
                                }


                            )


                        }


                    }


                }

                false -> {

                    Column {


                        pair.second.forEachIndexed { _, workItem ->

                            if (workItem.date == pair.first) {

                                sumR += workItem.sum


                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = 10.dp,
                                            top = 10.dp,
                                            bottom = 4.dp,
                                            end = 0.dp
                                        )
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Text(
                                        text = workItem.product_item_name + "\n• " + workItem.volume + " шт",
                                        modifier = Modifier.weight(2f),
                                        style = MaterialTheme.typography.titleSmall,


                                        )

                                    Spacer(modifier = Modifier.padding(10.dp))

                                    Text(
                                        text = Utils().fromDoubleToStringMoney(workItem.sum),
                                        style = MaterialTheme.typography.titleSmall,
                                        modifier = Modifier.weight(1f),
                                        maxLines = 1
                                    )
                                }


                            }


                        }


                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Badge(modifier = Modifier.padding(all = 0.dp),
                                content = {


                                    Text(
                                        style = MaterialTheme.typography.titleSmall,
                                        text = Utils().fromDoubleToStringMoney(sumR),
                                    )
                                }


                            )


                        }


                    }


                }


            }


        }


    }


}