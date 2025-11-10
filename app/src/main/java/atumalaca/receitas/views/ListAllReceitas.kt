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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import atumalaca.receitas.classes.Receita

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAllReceitas(navController: NavController) {
    // Temporary local state for recipes (replace with Firebase later)
    val receitas = rememberSaveable (saver = listSaver(
        save = { list -> list.map { it.nome } },
        restore = { list -> list.map { Receita(it, mapOf(), 0, "") } as SnapshotStateList<Receita>? }
    )) {
        mutableStateListOf<Receita>()
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Minhas Receitas") }
            )
        },
        floatingActionButton = {
            FloatingActionButton (onClick = {
                navController.navigate("AddReceita")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Receita")
            }
        }
    ) { padding ->
        if (receitas.isEmpty()) {
            // Show placeholder if there are no recipes
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhuma receita adicionada ainda ☕")
            }
        } else {
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(receitas) { receita ->
                    ReceitaCard(receita = receita){}
                }
            }
        }
    }
}

@Composable
fun ReceitaCard(receita: Receita, onClick: () -> Unit) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
    ) {
        Column (modifier = Modifier.padding(16.dp)) {
            Text(text = receita.nome, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Tempo de preparo: ${receita.tempoPreparo} min")
        }
    }
}