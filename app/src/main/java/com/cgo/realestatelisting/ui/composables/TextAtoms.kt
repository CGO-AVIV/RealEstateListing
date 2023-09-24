package com.cgo.realestatelisting.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.cgo.realestatelisting.ui.theme.AppTheme

@Composable
fun LabelText(text: String) {
    Text(
        text = text,
        maxLines = 1,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(
                end = AppTheme.dimens.verySmall
            )
    )
}

@Composable
fun ValueText(text: String) {
    Text(
        text = text,
        maxLines = 1,
        color = MaterialTheme.colorScheme.primary
    )
}