package com.kku.pharm.project.dmathere.utils

object DataFormatter {

    fun convertOneDigitToTwoDigit(input: Int): String{
        return if (input < 10 || input.toString().length == 1) {
            "0$input"
        } else input.toString()
    }
}