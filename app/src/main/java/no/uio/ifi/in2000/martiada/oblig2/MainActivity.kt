package no.uio.ifi.in2000.martiada.oblig2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.martiada.oblig2.ui.home.HomeScreen
import no.uio.ifi.in2000.martiada.oblig2.ui.home.HomeScreenViewModel
import no.uio.ifi.in2000.martiada.oblig2.ui.home.PartyViewModel
import no.uio.ifi.in2000.martiada.oblig2.ui.party.PartyDetailScreen
import no.uio.ifi.in2000.martiada.oblig2.ui.theme.Martiada_oblig2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val homeScreenViewModel: HomeScreenViewModel = viewModel()
            val partyViewModel: PartyViewModel = viewModel()

            val navController = rememberNavController()

            Martiada_oblig2Theme {
                NavHost(
                    navController = navController,
                    startDestination = "home",
                ) {
                    composable("home") { HomeScreen(navController, homeScreenViewModel) }
                    composable("partyScreen/{partyId}") { backStackEntry ->
                        val partyId = backStackEntry.arguments?.getString("partyId") ?: ""
                        PartyDetailScreen(
                            navController = navController,
                            partyId = partyId,
                            partyViewModel = partyViewModel
                        )
                    }
                }
            }
        }
    }
}