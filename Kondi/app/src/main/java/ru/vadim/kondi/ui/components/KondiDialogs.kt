package ru.vadim.kondi.ui.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.DialogProperties
import ru.vadim.kondi.utils.Utils
import ru.vadim.kondilibrary.calendar.CalendarDialog
import ru.vadim.kondilibrary.calendar.models.CalendarSelection
import ru.vadim.kondilibrary.core.models.base.Header
import ru.vadim.kondilibrary.core.models.base.SheetState
import ru.vadim.kondilibrary.list.ListDialog
import ru.vadim.kondilibrary.list.models.ListOption
import ru.vadim.kondilibrary.list.models.ListSelection
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * Диалог выбора даты
 * @param state В какой state устанавливать дату.
 * @param visible state видимости диалога.
 */
@Composable
fun KondiSetDateDialog(state: MutableState<String>, visible: MutableState<Boolean>, title: String) {


    val selectedDate =
        if (state.value != "") Utils().convertDateFromStringToLocalDate(state.value) else
            LocalDate.now()





    CalendarDialog(
        header = Header.Default(title),

        state = SheetState(
            visible = visible.value, embedded = true,
            onCloseRequest = { visible.value = false }
        ),

        selection = CalendarSelection.Date(
            onSelectDate = {
                state.value = it.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                visible.value = false
            },
            selectedDate = selectedDate,

            )
    )

}


@SuppressLint("SuspiciousIndentation")
@Composable
fun KondiSelectPeriodDialog(
    state: MutableState<Int>,
    visible: MutableState<Boolean>,
    listOptions: List<ListOption>,
    title: String,
    period: MutableState<String>
) {


    ListDialog(

        header = Header.Default(title),
        state = SheetState(visible = visible.value, onCloseRequest = { visible.value = false }),
        selection = ListSelection.Single(
            options = listOptions,
            withButtonView = false,

            ) { _, option ->


            state.value = option.partId
            period.value = option.titleText


        },
        properties = DialogProperties(dismissOnBackPress = true)

    )

}


