package atumalaca.receitas.classes

class ReceitaForno(
    nome: String,
    ingredientes: Map<String, String>,
    tempoPreparo: Int,
    modoPreparo: String,
    val tempoForno: Int,
    val temperatura: Int
) : Receita(nome, ingredientes, tempoPreparo, modoPreparo);