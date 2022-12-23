package com.somekoder.themepalette.domain.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb

fun Color.invertedLuminance() : Color {
    return if (luminance() > 0.50) {
        Color.Black
    }
    else {
        Color.White
    }
}

fun Color.hexToString() = String.format("#%06X", 0xFFFFFF and this.toArgb())