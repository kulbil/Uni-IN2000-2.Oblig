package no.uio.ifi.in2000.martiada.oblig2.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.End
import androidx.compose.ui.unit.dp
import no.uio.ifi.in2000.martiada.oblig2.model.votes.VotesDisplayMethod


@Composable
fun VoteList(
    votesUiState: VotesUiState,
    alpacaUiState: AlpacaUiState,
    displayMethodState: VotesDisplayMethodState,
    changeVoteDisplay: (VotesDisplayMethod) -> Unit,
) {

    var menuExpanded by remember { mutableStateOf(false) }

    val icon = if (menuExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown


    Box {
        OutlinedTextField(
            value = displayMethodState.displayMethod.name,
            onValueChange = {},
            label = { Text("Select what filter") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "Menu Arrow",
                    modifier = Modifier.clickable { menuExpanded = !menuExpanded }
                )
            }
        )
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false }
        ) {
            VotesDisplayMethod.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option.name) },
                    onClick = {
                        changeVoteDisplay(option)
                        menuExpanded = false
                    }
                )
            }
        }
    }



    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        when (votesUiState) {
            is VotesUiState.Loading -> Text(text = "Loading")
            is VotesUiState.Error -> Text(text = "Error")
            is VotesUiState.Success ->
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            text = "Parti"
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            text = "Antall stemmer",
                            textAlign = End
                        )
                    }
                    votesUiState.info.forEach { vote ->
                        val partyName = if (alpacaUiState is AlpacaUiState.Success) {
                            alpacaUiState.info.find { it.id == vote.alpacaPartyId }?.name
                                ?: "Unknown"
                        } else "Loading"

                        Row {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = partyName
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = vote.numberOfVotes.toString(),
                                textAlign = End
                            )
                        }
                    }
                }
        }
    }
}

