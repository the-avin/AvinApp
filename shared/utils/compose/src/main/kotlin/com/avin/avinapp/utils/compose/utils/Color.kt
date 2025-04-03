package com.avin.avinapp.utils.compose.utils

import androidx.compose.ui.graphics.Color

fun getColorForLetter(letter: Char) = Color(
    (letter.code * 3 + 123) % 256,
    (letter.code * 5 + 45) % 256,
    (letter.code * 7 + 32) % 256
)