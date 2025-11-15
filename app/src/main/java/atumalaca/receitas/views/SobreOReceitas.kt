package atumalaca.receitas.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SobreOReceitas(navController: NavController) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Sobre o App") },
                navigationIcon = {
                    IconButton (onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("App de Receitas 🍰", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Este aplicativo demonstra conceitos de Programação Orientada a Objetos (POO):")
            Text("• Classes e Objetos: cada receita é uma instância da classe Receita.")
            Text("• Herança: ReceitaForno e ReceitaBatedeira herdam de Receita.")
            Text("• Polimorfismo: cada tipo de receita implementa seu próprio modo de preparo.")
            Text("• Composables: 3 telas principais – Lista, Adicionar e Sobre.")
        }
    }
}