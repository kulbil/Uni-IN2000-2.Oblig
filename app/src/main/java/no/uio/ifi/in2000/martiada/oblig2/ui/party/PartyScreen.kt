package no.uio.ifi.in2000.martiada.oblig2.ui.party

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import no.uio.ifi.in2000.martiada.oblig2.ui.home.AlpacaPartyUiState
import no.uio.ifi.in2000.martiada.oblig2.ui.home.PartyViewModel

@OptIn(ExperimentalMaterial3Api::class) //TopAppBar Krever dette
@Composable
fun PartyDetailScreen(
    navController: NavController,
    partyId: String,
    partyViewModel: PartyViewModel = viewModel()
) {

    val alpacaPartyUiState by partyViewModel.alpacaPartyUiState.collectAsState()

    LaunchedEffect(partyId) {
        partyViewModel.getAlpacaPartyInfo(partyId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = when (val alpacaPartyUiState = alpacaPartyUiState) {
                        is AlpacaPartyUiState.Success -> Color(alpacaPartyUiState.info.color.toColorInt())
                        else -> Color.White
                    }
                )
            )
        },
        containerColor = when (val alpacaPartyUiState = alpacaPartyUiState) {
            is AlpacaPartyUiState.Success -> Color(lightenColor(alpacaPartyUiState.info.color))
            else -> Color.White
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = alpacaPartyUiState) {
                is AlpacaPartyUiState.Success -> {
                    val party = state.info
                    PartyInfo(
                        alpacaPartyUiStateImg = party.img,
                        alpacaPartyUiStateName = party.name,
                        alpacaPartyUiStateLeader = party.leader,
                        alpacaPartyUiStateColor = party.color,
                        alpacaPartyUiStateDesc = party.description

                    )
                }

                AlpacaPartyUiState.Loading -> {
                    Text("Loading")
                }

                AlpacaPartyUiState.Error -> {
                    Text("Error")
                }
            }
        }

    }


}


@Composable
fun PartyInfo(
    alpacaPartyUiStateImg: String,
    alpacaPartyUiStateName: String,
    alpacaPartyUiStateLeader: String,
    alpacaPartyUiStateColor: String,
    alpacaPartyUiStateDesc: String

) {

    Column(
        modifier = Modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AlpacaPicture(
            image = alpacaPartyUiStateImg,
            color = alpacaPartyUiStateColor
        )
        Text(
            text = alpacaPartyUiStateName,
            fontSize = 25.sp
        )
        Text(text = alpacaPartyUiStateLeader)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White)
                .padding(20.dp),
        ) {
            Text(text = alpacaPartyUiStateDesc)
        }

    }
}

@Composable
fun AlpacaPicture(
    image: String,
    color: String
) {
    AsyncImage(
        modifier = Modifier
            .clip(CircleShape)
            .height((100.dp))
            .border(
                width = 5.dp,
                color = Color(color.toColorInt()),
                shape = CircleShape
            )
            .aspectRatio(1f),
        model = image,
        contentDescription = "Party picture",
        contentScale = ContentScale.Crop
    )
}

fun lightenColor(color: String): Int {
    return ColorUtils.blendARGB(color.toColorInt(), 0xFFFFFFFF.toInt(), 0.7f)
}