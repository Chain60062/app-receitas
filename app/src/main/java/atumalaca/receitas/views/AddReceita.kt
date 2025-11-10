package atumalaca.receitas.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import atumalaca.receitas.repository.ReceitasRepository

@Composable
fun AddReceita(navController: NavController) {
    var nome by remember { mutableStateOf("") }
    var tempoPreparo by remember { mutableStateOf("") }
    var modoPreparo by remember { mutableStateOf("") }
    var tipoReceita by remember { mutableStateOf("Forno") }
    var temperatura by remember { mutableStateOf("") }
    var tempoForno by remember { mutableStateOf("") }
    var tempoBatendo by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val tipos = listOf("Comum", "Forno", "Batedeira")
    val receitasRepository = remember { ReceitasRepository() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Adicionar Receita", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome da Receita") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tempoPreparo,
            onValueChange = { tempoPreparo = it.filter { c -> c.isDigit() } },
            label = { Text("Tempo de Preparo (min)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = modoPreparo,
            onValueChange = { modoPreparo = it },
            label = { Text("Modo de Preparo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Dropdown de tipo de receita
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = tipoReceita,
                onValueChange = { /* readOnly */ },
                label = { Text("Tipo de Receita") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Abrir")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
            )

            // ⚡ Coloca o menu dentro do mesmo Box: ele vai aparecer logo abaixo do campo
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                tipos.forEach { tipo ->
                    DropdownMenuItem(
                        onClick = {
                            tipoReceita = tipo
                            expanded = false
                        }, text = {Text(tipo)}
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Campos específicos de cada tipo
        if (tipoReceita == "Forno") {
            OutlinedTextField(
                value = temperatura,
                onValueChange = { temperatura = it.filter { c -> c.isDigit() } },
                label = { Text("Temperatura (°C)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = tempoForno,
                onValueChange = { tempoForno = it.filter { c -> c.isDigit() } },
                label = { Text("Tempo de Forno (min)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        } else if(tipoReceita == "Batedeira"){
            OutlinedTextField(
                value = tempoBatendo,
                onValueChange = { tempoBatendo = it.filter { c -> c.isDigit() } },
                label = { Text("Tempo Batendo (min)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (nome.isNotBlank() && tempoPreparo.isNotBlank() && modoPreparo.isNotBlank()) {
                    receitasRepository.salvarReceita(
                        nome = nome,
                        ingredientes = mapOf(), // Pode abrir outra tela ou usar TextArea
                        tempoPreparo = tempoPreparo.toInt(),
                        modoPreparo = modoPreparo,
                        tipo = tipoReceita.lowercase(),
                        tempoForno = if (tipoReceita == "Forno") tempoForno.toIntOrNull() else null,
                        temperatura = if (tipoReceita == "Forno") temperatura.toIntOrNull() else null,
                        tempoBatendo = if (tipoReceita == "Batedeira") tempoBatendo.toIntOrNull() else null
                    )

                    navController.navigate("ListAllReceitas")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Receita")
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Cancelar")
        }
    }
}