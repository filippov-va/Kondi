package ru.vadim.kondi.ui.screens.period

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FolderDelete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.vadim.kondi.R
import ru.vadim.kondi.domain.period.PeriodItem
import ru.vadim.kondi.domain.work.WorkItem
import ru.vadim.kondi.ui.components.KondiEmptyListIndicator
import ru.vadim.kondi.ui.components.KondiInfoDialogIconButton
import ru.vadim.kondi.ui.components.KondiInputDateField
import ru.vadim.kondi.ui.components.KondiInputTextField
import ru.vadim.kondi.ui.components.base.KondiAddEditTopBar
import ru.vadim.kondi.ui.components.base.KondiElevatedCard3
import ru.vadim.kondi.ui.viewmodel.GlobalViewModel
import ru.vadim.kondi.utils.ScreenMode
import ru.vadim.kondi.utils.Utils

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PeriodAddEditScreen(
    globalViewModel: GlobalViewModel, navController: NavHostController
) {
    val screenMode by globalViewModel.periodScreenMode.observeAsState()
    val titleText = remember { mutableStateOf("") }
    val dateOpenState = remember {
        mutableStateOf("")
    }
    val descriptionState = remember { mutableStateOf("") }
    val dateCloseState = remember {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }
    val idState = remember { mutableStateOf("") }


    val onSaveWorksInPartsItem = {


        when (screenMode) {

            ScreenMode.Addition -> {


                val periodItem = PeriodItem(
                    dateStartPeriod = if (dateOpenState.value != "") Utils().convertDateFromStringToLong(
                        dateOpenState.value
                    ) else null,
                    dateEndPeriod = if (dateCloseState.value != "") Utils().convertDateFromStringToLong(
                        dateCloseState.value
                    ) else null,
                    worksList = listOf(),
                    item_name = descriptionState.value,
                )

                globalViewModel.addPeriodItem(periodItem)


            }

            ScreenMode.Editing -> {

                val old = globalViewModel.selectedPeriodItem.value
                var new =
                    PeriodItem(dateStartPeriod = null, dateEndPeriod = null, worksList = listOf())

                old?.let {

                    new = it.copy(
                        dateStartPeriod = if (dateOpenState.value != "") Utils().convertDateFromStringToLong(
                            dateOpenState.value
                        ) else null,
                        dateEndPeriod = if (dateCloseState.value != "") Utils().convertDateFromStringToLong(
                            dateCloseState.value
                        ) else null,
                        item_name = descriptionState.value,
                        id = idState.value.toInt()
                    )


                }


                globalViewModel.updatePeriodItem(
                    new
                )
            }

            else -> {}
        }

        navController.popBackStack()


    }

    LaunchedEffect(null) {

        when (screenMode) {

            ScreenMode.Addition -> {
                titleText.value = "Добавление периода"
            }

            ScreenMode.Editing -> {
                titleText.value = "Редактирование периода"


                val selectedWorksInPartsItem = globalViewModel.selectedPeriodItem

                idState.value = selectedWorksInPartsItem.value?.id?.toString() ?: ""
                descriptionState.value = selectedWorksInPartsItem.value?.item_name ?: "null"
                dateOpenState.value =
                    Utils().convertDateFromLongToString(selectedWorksInPartsItem.value?.dateStartPeriod)
                        ?: ""
                dateCloseState.value =
                    Utils().convertDateFromLongToString(selectedWorksInPartsItem.value?.dateEndPeriod)
                        ?: ""
            }

            else -> {}
        }

    }

    DisposableEffect(null) {

        this.onDispose {
            globalViewModel.setPeriodScreenMode(ScreenMode.Reading)

        }


    }



    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,

        topBar = {


            KondiAddEditTopBar(


                headerText = titleText.value,

                actions = {

                    when (screenMode) {

                        ScreenMode.Addition -> {


                            IconButton(
                                enabled = descriptionState.value.trimMargin() != "",
                                onClick = {

                                    onSaveWorksInPartsItem.invoke()


                                })
                            {

                                Icon(
                                    imageVector = Icons.Filled.Save,
                                    contentDescription = "",
                                    tint = if (descriptionState.value.trimMargin() != "") MaterialTheme.colorScheme.secondaryContainer else
                                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4F)
                                )

                            }

                            KondiInfoDialogIconButton(R.string.WorksInPartsScreenModeAddInfo)


                        }

                        ScreenMode.Editing -> {

                            IconButton(

                                onClick = {

                                    globalViewModel.selectedPeriodItem.value?.let {
                                        globalViewModel.deleteWorksInPartsItem(it)
                                        navController.popBackStack()
                                    }


                                })
                            {

                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "",
                                )

                            }


                            IconButton(
                                enabled = descriptionState.value.trimMargin() != "",
                                onClick = {

                                    onSaveWorksInPartsItem.invoke()


                                })
                            {

                                Icon(
                                    imageVector = Icons.Filled.Save,
                                    contentDescription = "",
                                    tint = if (descriptionState.value.trimMargin() != "") MaterialTheme.colorScheme.secondaryContainer else
                                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4F)
                                )

                            }





                            KondiInfoDialogIconButton(R.string.WorksInPartsScreenModeEditInfo)


                        }

                        else -> {}
                    }


                }, navController = navController, navigationIconClick = { }

            )


        },

        content = {
            val pagerState = rememberPagerState()


            when (screenMode) {

                ScreenMode.Addition -> {

                    Column(
                        Modifier.padding(
                            top = 5.dp,
                            bottom = 0.dp,
                            start = 15.dp,
                            end = 15.dp
                        )
                    ) {
                        KondiInputTextField(
                            label = "Наименование периода",
                            state = descriptionState,
                            onValueChange = { descriptionState.value = it },
                            focusRequester = focusRequester,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
                            ),
                            trailingIcon = Icons.Outlined.Clear,
                            modifier = Modifier
                        )


                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 25.dp, bottom = 0.dp),
                            text = "Даты интервала рабочих смен",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.5f
                            ),

                            )

                        KondiInputDateField(
                            state = dateOpenState,
                            "Дата начала",
                            trailingIcon = Icons.Filled.Clear,
                            title = "Дата начала",
                            trailingAction = {
                                it.value = ""
                            })

                        KondiInputDateField(
                            state = dateCloseState,
                            "Дата окончания",
                            trailingIcon = Icons.Filled.Clear,
                            title = "Дата окончания",
                            trailingAction = {
                                it.value = ""
                            })

                    }


                }


                ScreenMode.Editing -> {

                    val pages = listOf(Tabs.General, Tabs.ListWorks, Tabs.Statistics)

                    Column(modifier = Modifier.fillMaxSize()) {


                        HorizontalTabs(pages, pagerState = pagerState)

                        HorizontalPager(
                            count = pages.size,
                            state = pagerState,
                            modifier = Modifier.padding(top = 0.dp)
                        ) { currentPage ->


                            when (currentPage) {

                                0 -> {


                                    Column(
                                        modifier = Modifier
                                            .padding(
                                                top = 5.dp,
                                                bottom = 0.dp,
                                                start = 15.dp,
                                                end = 15.dp
                                            )
                                            .fillMaxSize()
                                    ) {

                                        KondiInputTextField(
                                            label = "Наименование периода",
                                            state = descriptionState,
                                            onValueChange = { descriptionState.value = it },
                                            focusRequester = focusRequester,
                                            keyboardOptions = KeyboardOptions(
                                                imeAction = ImeAction.Next,
                                                keyboardType = KeyboardType.Text
                                            ),
                                            trailingIcon = Icons.Outlined.Clear,
                                            modifier = Modifier
                                        )


                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 25.dp, bottom = 0.dp),
                                            text = "Даты интервала рабочих смен",
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colorScheme.onBackground.copy(
                                                alpha = 0.5f
                                            ),

                                            )

                                        KondiInputDateField(
                                            state = dateOpenState,
                                            "Дата начала",
                                            trailingIcon = Icons.Filled.Clear,
                                            trailingAction = { it.value = "" },
                                            title = "Дата начала"
                                        )

                                        KondiInputDateField(
                                            state = dateCloseState,
                                            "Дата окончания",
                                            trailingIcon = Icons.Filled.Clear,
                                            trailingAction = {
                                                it.value = ""
                                            },
                                            title = "Дата окончания"
                                        )
                                    }

                                }


                                1 -> {


                                    Box(
                                        Modifier
                                            .fillMaxSize()
                                            .padding(start = 15.dp, end = 15.dp)
                                    ) {


                                        globalViewModel.selectedPeriodItem.observeAsState().value?.worksList?.let {

                                            if (it.isEmpty()) {
                                                KondiEmptyListIndicator(text = "Нет записей")
                                            } else {

                                                GridWorksItems(it, onCardClick = {


                                                }, onDeleteClick = {
                                                    globalViewModel.deleteWorkItemFromPeriodItem(periodId =  idState.value.toInt(), it)

                                                })


                                            }

                                        }


                                    }


                                }

                                2 -> {

                                    KondiEmptyListIndicator(text = "В разработке :)")

                                }
                            }


                        }


                    }


                }


                else -> {}
            }


        }


    )


}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalTabs(
    items: List<Tabs>,
    pagerState: PagerState,
    scope: CoroutineScope = rememberCoroutineScope()
) {

    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background, elevation = 4.dp) {

        for (page in items.indices) {

            BottomNavigationItem(selected = page == pagerState.currentPage,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(page = page)
                    }
                },
                icon = {

                    androidx.compose.material.Icon(
                        imageVector = items[page].icon,
                        contentDescription = null,
                    )


                },
                selectedContentColor = MaterialTheme.colorScheme.secondaryContainer,
                unselectedContentColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),
                label = {
                    androidx.compose.material.Text(
                        text = items[page].headerText
                    )

                })


        }

    }


}


sealed class Tabs(val headerText: String, val icon: ImageVector) {

    object General : Tabs("Общее", Icons.Filled.AdminPanelSettings)
    object ListWorks : Tabs("Записи", Icons.Filled.List)
    object Statistics : Tabs("Статистика", Icons.Filled.Info)

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridWorksItems(workItems: List<WorkItem>,  onCardClick: (WorkItem) -> Unit, onDeleteClick: (WorkItem) -> Unit) {


    val grouped = workItems.toList().sortedByDescending { it.date }.groupBy { it.date }

    fun LazyGridScope.header(
        content: @Composable LazyGridItemScope.() -> Unit
    ) {
        item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
    }

    Column() {


        LazyColumn {

            grouped.forEach { (date, workItemList) ->


                stickyHeader {

                    Row(
                        modifier = Modifier
                            .padding(all = 3.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Badge(

                            modifier = Modifier

                                .wrapContentWidth()
                                .padding(all = 4.dp), content = {


                                Text(
                                    text = Utils().convertDateFromLongToString(date).toString(),
                                    style = MaterialTheme.typography.labelLarge,
                                )


                            })

                    }


                }


                items(items = workItemList) { workItem ->


                    KondiElevatedCard3(onCardClick = { onCardClick.invoke(workItem)  }) {

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

                            Spacer(modifier = Modifier.size(5.dp))

                            Box(
                                modifier = Modifier.sizeIn(maxWidth = 20.dp, maxHeight = 20.dp),
                                contentAlignment = Alignment.TopCenter
                            ){



                                IconButton(onClick = {

                                    onDeleteClick.invoke(workItem)

                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "",
                                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground.copy(
                                            alpha = 0.7f
                                        )
                                    )
                                }


                            }
                        }


                    }
                }


                /*
    
        LazyVerticalGrid(
            modifier = Modifier.padding(top = 0.dp),
            columns = GridCells.Fixed(2)
        ) {
    
    
            grouped.forEach { (date, worksForInitial) ->
    
    
                header {
    
                    Column(
                        modifier = Modifier.padding(top = 10.dp, start = 5.dp, end = 5.dp)
    
                    ) {
    
                        Badge(content = {
                            androidx.compose.material3.Text(
                                style = MaterialTheme.typography.labelLarge,
                                text = Utils().convertDateFromLongToString(date).toString(),
    
                                )
                        })
                    }
    
    
                }
    
                items(items = worksForInitial) { workItem ->
    
                    KondiElevatedCard2(workItem.product_item_name, onCardClick = {}) {
    
    
                        Column(modifier = Modifier.fillMaxSize()) {
                            androidx.compose.material3.Text(
                                text = workItem.volume.toString() + " шт",
                                style = MaterialTheme.typography.titleSmall,
    
    
                                )
                            Spacer(modifier = Modifier.padding(2.dp))
    
    
    
                            Badge(modifier = Modifier.padding(all = 3.dp),
                                content = {
    
    
                                    androidx.compose.material3.Text(
                                        fontSize = androidx.compose.material.MaterialTheme.typography.subtitle2.fontSize,
                                        text = Utils().fromDoubleToStringMoney(workItem.sum),
    
                                        )
                                }
    
    
                            )
    
    
                        }
    
    
                    }
                }
    
    
            }
    
    
            /*
                    items(items = datas){
                        var sum = 0.0
    
                        sum += it.sum
    
    
                        KondiElevatedCard2( Utils().convertDateFromLongToString(it.date).toString(), onCardClick = {}  ){
    
    
                            Column(modifier = Modifier.fillMaxSize()) {
                                androidx.compose.material3.Text(
                                    text = it.product_item + "\n• " + it.volume + " шт",
                                    style = MaterialTheme.typography.titleSmall,
    
    
                                    )
                                Spacer(modifier = Modifier.padding(10.dp))
    
    
                                androidx.compose.material3.Text(
                                    text = Utils().fromDoubleToStringMoney(it.sum),
                                    style = MaterialTheme.typography.titleSmall,
    
                                    maxLines = 1
                                )
    
                            }
    
    
    
    
    
                        }
    
    
    
    
                    }
    
            */
    
    
        }
    
    */
            }
        }
    }
}

   