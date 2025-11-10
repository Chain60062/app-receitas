package atumalaca.receitas.classes

class ReceitaBatedeira(
    nome: String,
    ingredientes: Map<String, String>,
    tempoPreparo: Int,
    modoPreparo: String,
    val tempoBatendo: Int,
) : Receita(nome, ingredientes, tempoPreparo, modoPreparo);