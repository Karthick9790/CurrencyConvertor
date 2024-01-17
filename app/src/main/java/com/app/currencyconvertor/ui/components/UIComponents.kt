package com.app.currencyconvertor.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.app.currencyconvertor.ui.screen.convertor.UIState

@Composable
fun AppProgress(
    modifier: Modifier = Modifier,
    isVisible: Boolean
) {
    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun AppError(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    message: String,
) {
    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier
    ) {
        Toast.makeText(
            LocalContext.current,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}