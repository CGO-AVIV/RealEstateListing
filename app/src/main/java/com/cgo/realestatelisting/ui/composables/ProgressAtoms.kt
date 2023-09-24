package com.cgo.realestatelisting.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cgo.realestatelisting.ui.theme.AppTheme

@Composable
fun IndeterminateProgress() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .height(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            strokeWidth = AppTheme.dimens.verySmall
        )
    }
}