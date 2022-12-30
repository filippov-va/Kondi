package ru.vadim.kondi.ui.screens.period

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.vadim.kondi.R
import ru.vadim.kondi.domain.period.PeriodItem
import ru.vadim.kondi.navigation.Screens
import ru.vadim.kondi.ui.components.KondiEmptyListIndicator
import ru.vadim.kondi.ui.components.base.KondiMainTopBar
import ru.vadim.kondi.ui.components.base.KondiElevatedCard
import ru.vadim.kondi.ui.components.base.KondiInfoDialogIconButton
import ru.vadim.kondi.ui.components.base.KondiWorksElevatedCard
import ru.vadim.kondi.ui.viewmodels.GlobalViewModel
import ru.vadim.kondi.utils.ScreenMode
import ru.vadim.kondi.utils.Utils


@SuppressLint(
    "CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter"
)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorksScreenInParts(
    globalViewModel: GlobalViewModel,
    navController: NavHostController,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit
) {

    val partsVolume = remember {
        mutableStateOf(0)
    }


    val listAllWorksList = globalViewModel.allWorksInParts.observeAsState(mutableListOf())

    Scaffold(
        topBar = {
            KondiMainTopBar(headerText = "Периоды", openDrawer = openDrawer, actions = {

                IconButton(onClick = {
                    globalViewModel.deleteAllWorksInPartsItems()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "",

                        )
                }

                IconButton(onClick = {
                    globalViewModel.setWorksInPartsScreenMode(ScreenMode.Addition)
                    navController.navigate(Screens.PeriodAddEditScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",

                        )
                }

                KondiInfoDialogIconButton(stringId = R.string.WorksInPartsScreenInfo)




            })
        },

        content = {
            if (listAllWorksList.value.isNotEmpty()) {

                partsVolume.value = listAllWorksList.value.size

                Column(
                    modifier = Modifier
                        //  .fillMaxSize()
                        .background(Color.Transparent)
                ) {


                    WorksInPartsListGrid(listAllWorksList, 1) {
                        EditWorksInParts(globalViewModel, it, navController)
                    }


                }
            } else {

                partsVolume.value = 0
                KondiEmptyListIndicator(text = "Нет записей")
            }


        },

        backgroundColor = MaterialTheme.colorScheme.background
    )


}


@ExperimentalFoundationApi
@Composable
fun WorksInPartsListGrid(
    data: State<List<PeriodItem>>,
    cellsCount: Int = 2,
    onLongCardClick: (PeriodItem) -> Unit
) {


    LazyVerticalGrid(


        modifier = Modifier.padding(top = 1.dp, start = 10.dp, end = 10.dp)

        , columns = GridCells.Fixed(cellsCount), content = {


            val datas = data.value.sortedByDescending { it.dateStartPeriod }.toList()




            items(items = datas) { worksInPartsItem ->

                var sum = 0.0

                if (worksInPartsItem.worksList != null) {
                    val listWorks = worksInPartsItem.worksList.toList()

                    sum = listWorks.toList().sumByDouble { it.sum }
                }




                KondiElevatedCard(
                    modifier = Modifier.padding(top = 15.dp),
                    titleText = worksInPartsItem.item_name,
                    onCardClick = {


                    },
                    onActionClick = {   onLongCardClick(worksInPartsItem) }) {



                    Column(
                        modifier = Modifier.padding(5.dp)
                    ) {


                        var dates = ""

                        dates += if (worksInPartsItem.dateStartPeriod != null) Utils().convertDateFromLongToString(
                            dateLong = worksInPartsItem.dateStartPeriod
                        ) else "..."

                        dates += " - "

                        dates += if (worksInPartsItem.dateEndPeriod != null) Utils().convertDateFromLongToString(
                            dateLong = worksInPartsItem.dateEndPeriod
                        ) else "..."


                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(bottom = 2.dp)) {
                            Text(text = dates,
                                style = MaterialTheme.typography.titleSmall, )

                            Badge(modifier = Modifier.padding(all = 0.dp),
                                content = {


                                    Text(
                                        style = MaterialTheme.typography.titleSmall,
                                        text = "${Utils().fromDoubleToStringMoney(sum)}"

                                        )
                                } )


                        }






                        worksInPartsItem.worksList?.let { list ->


                            val datas = list.sortedByDescending { it.date }.toList()
                            val grouped = datas.groupBy { it.date }.toList()








                            grouped.forEachIndexed { _, pair ->



                                KondiWorksElevatedCard(pair)








                            }



                        }


                    }
















                }





            }


        })

}


fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}



private fun EditWorksInParts(
    globalViewModel: GlobalViewModel,
    periodItem: PeriodItem,
    navController: NavHostController
) {

    globalViewModel.setWorksInPartsScreenMode(ScreenMode.Editing)

    globalViewModel.selectWorksInPartsItem(periodItem.id)

    Utils().executeAfterDelay {
        navController.navigate(Screens.PeriodAddEditScreen.route)
    }
}


