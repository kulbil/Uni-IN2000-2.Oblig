package no.uio.ifi.in2000.martiada.oblig2.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage


@Composable
fun AlpacaCard(
    modifier: Modifier = Modifier,
    navAction: () -> Unit,
    alpacaUiStateImg: String,
    alpacaUiStateName: String,
    alpacaUiStateLeader: String,
    alpacaUiStateColor: String,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { navAction() }
            .height(100.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(alpacaUiStateColor.toColorInt())),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {


        Box(
            modifier = modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(70.dp)
                    .width(70.dp)
                    .clip(CircleShape)
                    .aspectRatio(1f),
                model = alpacaUiStateImg,
                contentDescription = "Alpaca",
                contentScale = ContentScale.Crop
            )
        }


        Column(
            modifier = Modifier.weight(2f)
        ) {
            Box(
                modifier = modifier
                    .weight(2f),
                contentAlignment = Alignment.CenterStart
            ) { Text(text = alpacaUiStateName, fontSize = 20.sp) }

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Leader: $alpacaUiStateLeader",
                    Modifier.padding(end = 20.dp, bottom = 5.dp)
                )
            }
        }
    }
}