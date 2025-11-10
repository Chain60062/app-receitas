package atumalaca.receitas.classes


class ReceitaBatedeira(
    nome: String = "",
    ingredientes: Map<String, String> = emptyMap(),
    tempoPreparo: Int = 0,
    modoPreparo: String = "",
    var tempoBatendo: Int = 0
) : Receita(nome, ingredientes, tempoPreparo, modoPreparo) {
    constructor() : this("", emptyMap(), 0, "", 0)
}