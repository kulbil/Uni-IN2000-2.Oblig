package no.uio.ifi.in2000.martiada.oblig1

import kotlin.math.round

enum class ConverterUnits {
    OUNCE,
    CUP,
    GALLON,
    HOGSHEAD
}

fun converter(num: Int, unit: ConverterUnits): Int {
    val converted = when (unit) {
        ConverterUnits.OUNCE -> { num * 0.02957 }
        ConverterUnits.CUP -> { num * 0.23659 }
        ConverterUnits.GALLON -> { num * 3.78541 }
        else -> { num * 238.481 }
    }

    return (round(converted)).toInt()
}