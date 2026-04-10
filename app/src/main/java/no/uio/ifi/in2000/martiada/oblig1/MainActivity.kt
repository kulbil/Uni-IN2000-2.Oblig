package no.uio.ifi.in2000.martiada.oblig1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import no.uio.ifi.in2000.martiada.oblig1.palindrome.PalindromeCheckerScreen
import no.uio.ifi.in2000.martiada.oblig1.ui.theme.Martiada_oblig1Theme
import no.uio.ifi.in2000.martiada.oblig1.unitconverter.UnitConverterScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Martiada_oblig1Theme {
                Scaffold(
                    bottomBar = { NavigationBar(navController = navController) }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "palindrome",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("palindrome") { PalindromeCheckerScreen() }
                        composable("unit_converter") { UnitConverterScreen() }
                    }
                }
            }
        }
    }
}

