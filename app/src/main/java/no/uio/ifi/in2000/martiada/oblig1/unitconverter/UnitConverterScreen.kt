package no.uio.ifi.in2000.martiada.oblig1.unitconverter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UnitConverterScreen() {
    UnitConverter(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize()
    )
}


