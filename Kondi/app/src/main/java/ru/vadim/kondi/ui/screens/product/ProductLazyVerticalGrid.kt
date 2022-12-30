package ru.vadim.kondi.ui.screens.product


import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ru.vadim.kondi.domain.product.ProductItem
import ru.vadim.kondi.ui.components.base.KondiElevatedCard
import ru.vadim.kondi.utils.Utils


fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun ProductLazyVerticalGrid(
    data: List<ProductItem>,
    cellsCount: Int = 2,
    onCardClick: (ProductItem) -> Unit,
    onActionEditClick: (ProductItem) -> Unit,
) {

    val grouped = data.toList().sortedBy { it.item_name[0] }.groupBy { it.item_name[0] }

    LazyVerticalGrid(modifier = Modifier
        .padding(top = 1.dp, start = 5.dp, end = 5.dp)
        .fillMaxSize(),
        columns = GridCells.Fixed(cellsCount),
        content = {


            grouped.forEach { (initial, productsForInitial) ->


                header {

                    Column(
                        modifier = Modifier.padding(top = 10.dp, start = 5.dp, end = 5.dp)

                    ) {

                        Badge(content = {
                            Text(
                                style = MaterialTheme.typography.labelLarge,
                                text = initial.toString()
                            )
                        })
                    }


                }





                items(items = productsForInitial) { productItem ->


                    KondiElevatedCard(titleText = productItem.item_name,
                        onCardClick = { onCardClick(productItem) },
                        onActionClick = { onActionEditClick(productItem) }) {

                        Column {


                            Text(buildAnnotatedString {


                                withStyle(style = SpanStyle(fontSize = androidx.compose.material.MaterialTheme.typography.subtitle2.fontSize)) {
                                    append(
                                        "\nСтоимость килограмма: "
                                    )
                                }


                                withStyle(style = SpanStyle(fontSize = androidx.compose.material.MaterialTheme.typography.subtitle2.fontSize)) {
                                    append(Utils().fromDoubleToStringMoney(productItem.rubles.toDouble() + (productItem.kopeck * 0.01)))
                                }

                                withStyle(style = SpanStyle(fontSize = androidx.compose.material.MaterialTheme.typography.subtitle2.fontSize)) {
                                    append(
                                        "\nВес изделия: "
                                    )
                                }

                                withStyle(style = SpanStyle(fontSize = androidx.compose.material.MaterialTheme.typography.subtitle2.fontSize)) {
                                    append("${productItem.kilogram + (productItem.gram * 0.001)} кг.\n")
                                }

                            })


                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Badge(modifier = Modifier.padding(all = 3.dp), content = {


                                    Text(
                                        fontSize = androidx.compose.material.MaterialTheme.typography.subtitle2.fontSize,
                                        text = Utils().fromDoubleToStringMoney(
                                            (productItem.rubles + (productItem.kopeck * 0.01)) * (productItem.kilogram.toDouble() + (productItem.gram * 0.001))
                                        ) + "",

                                        )
                                }


                                )


                            }


                        }
                    }


                }


            }

        })

}
