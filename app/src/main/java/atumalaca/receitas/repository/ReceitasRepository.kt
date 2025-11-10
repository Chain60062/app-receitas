package atumalaca.receitas.repository

import atumalaca.receitas.classes.Receita
import atumalaca.receitas.datasource.DataSource
import kotlinx.coroutines.flow.Flow

class ReceitasRepository {
    private val dataSource = DataSource()

    fun listarReceitas(): Flow<MutableList<Receita>> {
        return dataSource.listarReceitas()
    }

    fun salvarReceita(
        nome: String,
        ingredientes: Map<String, String>,
        tempoPreparo: Int,
        modoPreparo: String,
        tipo: String,
        tempoForno: Int? = null,
        temperatura: Int? = null,
        tempoBatendo: Int? = null
    ) {
        dataSource.salvarReceita(
            nome = nome,
            ingredientes = ingredientes,
            tempoPreparo = tempoPreparo,
            modoPreparo = modoPreparo,
            tipo = tipo,
            tempoForno = tempoForno,
            temperatura = temperatura,
            tempoBatendo = tempoBatendo
        )
    }

    fun removerReceita(docId: String) {
        dataSource.removerReceita(docId)
    }
}