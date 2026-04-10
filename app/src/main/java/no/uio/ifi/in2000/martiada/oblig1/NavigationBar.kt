package no.uio.ifi.in2000.martiada.oblig1

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NavigationBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavButton(
            btnText ="Palindrome",
            modifier = Modifier
                .weight(1f) //Klarte ikke å bli kvitt denne modifieren fra parameteret.
                .fillMaxHeight(),
            navClick = { navController.navigate("palindrome") }
        )
        NavButton(
            btnText ="Unit Converter",
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            navClick = { navController.navigate("unit_converter") }
        )
    }
}

@Composable
fun NavButton(btnText: String, modifier: Modifier = Modifier, navClick: () -> Unit) {
    Button(
        onClick = navClick,
        modifier = modifier,
        shape = RectangleShape, //Vet at android material stilen er mer rund, men likte denne looken
    ) {
        Text(text = btnText)
    }
}