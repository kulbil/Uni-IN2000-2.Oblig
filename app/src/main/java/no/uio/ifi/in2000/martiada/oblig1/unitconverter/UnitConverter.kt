package no.uio.ifi.in2000.martiada.oblig1.unitconverter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import no.uio.ifi.in2000.martiada.oblig1.ConverterUnits
import no.uio.ifi.in2000.martiada.oblig1.converter

@Composable
fun UnitConverter(modifier: Modifier = Modifier) {
    //Drop down menu varables
    var selectedUnit by remember { mutableStateOf(ConverterUnits.OUNCE) }
    var menuExpanded by remember { mutableStateOf(false) }
    val icon = if (menuExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    //Liter and result variables
    var literAmount by remember { mutableIntStateOf(0) }
    var convertedResult by remember { mutableIntStateOf(0) }
    var literAmountText by remember { mutableStateOf("") }

    //A reference to the software keyboard so i can .hide() it later
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box() {
            OutlinedTextField(
                value = selectedUnit.name,
                onValueChange = { newUnit -> selectedUnit = ConverterUnits.valueOf(newUnit) },
                label = { Text("Select unit") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Menu arrow",
                        modifier = Modifier.clickable { menuExpanded = !menuExpanded }
                    )
                }
            )
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                ConverterUnits.entries.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(text = unit.name) },
                        onClick = {
                            selectedUnit = unit
                            menuExpanded = false
                            convertedResult = converter(literAmount, selectedUnit)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = literAmountText,
            onValueChange = { newAmount ->
                literAmountText = newAmount
                literAmount = newAmount.toIntOrNull() ?: 0
                convertedResult = converter(literAmount, selectedUnit)
            },
            label = { Text("Type in liters") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide() //Fikk litt hjelp fra KI her
                    convertedResult = converter(literAmount, selectedUnit)
                    literAmountText = literAmount.toString()
                }

            )

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${literAmountText.ifEmpty{ "0" }} ${selectedUnit.name.lowercase()}s is ${convertedResult}L"
        )
        //literAmountText.ifEmpty{ "0" } ble forklart og lært av KI

    }
}