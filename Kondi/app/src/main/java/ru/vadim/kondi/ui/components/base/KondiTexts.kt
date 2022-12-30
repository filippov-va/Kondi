package ru.vadim.kondi.ui.components.base

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight


@Composable
fun KondiHeaderText(headerText: String) {

    Text(
        text = headerText,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
    )

}
