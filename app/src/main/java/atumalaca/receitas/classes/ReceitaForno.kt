package atumalaca.receitas.classes

class ReceitaForno(
    nome: String = "",
    ingredientes: Map<String, String> = emptyMap(),
    tempoPreparo: Int = 0,
    modoPreparo: String = "",
    val tempoForno: Int = 0,
    val temperatura: Int = 0
) : Receita(nome, ingredientes, tempoPreparo, modoPreparo) {
    override fun descricaoEquipamentos(): String {
        return "Esta receita requer o uso de um forno."
    }
    constructor() : this("", emptyMap(), 0, "", 0, 0)
}