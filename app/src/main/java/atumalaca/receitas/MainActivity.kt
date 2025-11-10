package atumalaca.receitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import atumalaca.receitas.ui.theme.ReceitasTheme
import atumalaca.receitas.views.AddReceita
import atumalaca.receitas.views.ListAllReceitas
import atumalaca.receitas.views.SobreOReceitas

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceitasTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "ListAllReceitas"
                ) {
                    composable(
                        route = "ListAllReceitas"
                    ) {
                        ListAllReceitas(navController)
                    }

                    composable(
                        route = "AddReceita"
                    ) {
                        AddReceita(navController)
                    }

                    composable(
                        route = "SobreOReceitas"
                    ) {
                        SobreOReceitas(navController)
                    }
                }
            }
        }
    }
}
