package com.cgo.realestatelisting.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.cgo.realestatelisting.ui.theme.AppTheme

@Composable
fun ActionableMessage(
    message: String,
    buttonMessage: String,
    actionable: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(48.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                modifier = Modifier.padding(AppTheme.dimens.small),
                color = MaterialTheme.colorScheme.tertiary,
                textAlign = TextAlign.Center
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                onClick = actionable
            ) {
                Text(
                    text = buttonMessage,
                    modifier = Modifier.padding(AppTheme.dimens.verySmall),
                    fontSize = TextUnit(16F, TextUnitType.Sp)
                )
            }
        }
    }
}