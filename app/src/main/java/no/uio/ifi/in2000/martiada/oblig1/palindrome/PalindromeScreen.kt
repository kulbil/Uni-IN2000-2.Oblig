package no.uio.ifi.in2000.martiada.oblig1.palindrome

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PalindromeCheckerScreen() {
    PalindromeChecker(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize()
    )
}
