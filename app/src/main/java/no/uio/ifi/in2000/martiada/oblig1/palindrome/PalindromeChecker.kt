package no.uio.ifi.in2000.martiada.oblig1.palindrome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import no.uio.ifi.in2000.martiada.oblig1.isPalindrome

@Composable
fun PalindromeChecker(modifier: Modifier = Modifier) {
    var result: Boolean? by remember { mutableStateOf(null) }
    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Type Palindrome") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { result = isPalindrome(text) })
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = when (result) {
                true -> "Word is a palindrome"
                false -> "Word is not a palindrome"
                else -> "Type in a word to check if its a palindrome"
            }
        )
    }
}