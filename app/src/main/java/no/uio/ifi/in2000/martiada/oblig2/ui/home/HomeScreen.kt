package no.uio.ifi.in2000.martiada.oblig2.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import no.uio.ifi.in2000.martiada.oblig2.model.votes.VotesDisplayMethod


@Composable
fun HomeScreen(
    navController: NavController,
    homeScreenViewModel: HomeScreenViewModel
) {

    val alpacaUiState by homeScreenViewModel.alpacaUiState.collectAsState()
    val votesUiState by homeScreenViewModel.votesUiState.collectAsState()
    val votesDisplayMethodState by homeScreenViewModel.votesDisplayMethodState.collectAsState()
    val changeVoteDisplay: (VotesDisplayMethod) -> Unit = homeScreenViewModel::changeVoteDisplay


    when (val alpacaUiState = alpacaUiState) {
        is AlpacaUiState.Loading -> Text(
            text = "Loading"
        )

        is AlpacaUiState.Success ->
            Box(
                modifier = Modifier.padding(
                    top = 50.dp,
                    start = 20.dp,
                    end = 20.dp
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(alpacaUiState.info.size) { index ->
                            val party = alpacaUiState.info[index]
                            AlpacaCard(
                                modifier = Modifier,
                                navAction = { navController.navigate("partyScreen/${party.id}") },
                                alpacaUiStateImg = party.img,
                                alpacaUiStateName = party.name,
                                alpacaUiStateLeader = party.leader,
                                alpacaUiStateColor = party.color,
                            )
                        }
                    }
                    VoteList(
                        votesUiState = votesUiState,
                        alpacaUiState = alpacaUiState,
                        displayMethodState = votesDisplayMethodState,
                        changeVoteDisplay = changeVoteDisplay,
                    )
                }

            }

        is AlpacaUiState.Error -> Text(
            text = "Error"
        )
    }
}