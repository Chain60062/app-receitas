package atumalaca.receitas.classes

class ReceitaForno(
    nome: String = "",
    ingredientes: Map<String, String> = emptyMap(),
    tempoPreparo: Int = 0,
    modoPreparo: String = "",
    var tempoForno: Int = 0,
    var temperatura: Int = 0
) : Receita(nome, ingredientes, tempoPreparo, modoPreparo) {
    constructor() : this("", emptyMap(), 0, "", 0, 0)
}