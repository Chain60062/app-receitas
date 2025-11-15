package atumalaca.receitas.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import atumalaca.receitas.classes.Receita
import atumalaca.receitas.classes.ReceitaBatedeira
import atumalaca.receitas.classes.ReceitaForno
import atumalaca.receitas.repository.ReceitasRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAllReceitas(navController: NavController) {
    val repository = remember { ReceitasRepository() }
    val receitas by repository.listarReceitas().collectAsState(initial = mutableListOf())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Receitas") },
                actions = {
                    IconButton(onClick = { navController.navigate("ListAllReceitas") }) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }

                    IconButton(onClick = { navController.navigate("SobreOReceitas") }) {
                        Icon(Icons.Default.Info, contentDescription = "Sobre o App")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("AddReceita") },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Receita")
            }
        },
    ) { paddingValues ->
        if (receitas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhuma receita adicionada ainda")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(receitas) { receita ->
                    ReceitaCard(receita = receita) { }
                }
            }
        }
    }
}


@Composable
fun ReceitaCard(receita: Receita, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Display common fields
            Text(text = receita.nome, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Tempo de preparo: ${receita.tempoPreparo} min")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Modo de preparo: ${receita.modoPreparo}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Equipamentos: ${receita.descricaoEquipamentos()}")
            Spacer(modifier = Modifier.height(8.dp))
            when (receita) {
                is ReceitaBatedeira -> {
                    Text(
                        text = "Tempo batendo: ${receita.tempoBatendo} min"
                    )
                }
                is ReceitaForno -> {
                    Text(
                        text = "Tempo de forno: ${receita.tempoForno} min"
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Temperatura: ${receita.temperatura}°C"
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}