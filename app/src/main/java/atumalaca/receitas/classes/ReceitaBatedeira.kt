package atumalaca.receitas.classes


class ReceitaBatedeira(
    nome: String = "",
    ingredientes: Map<String, String> = emptyMap(),
    tempoPreparo: Int = 0,
    modoPreparo: String = "",
    val tempoBatendo: Int = 0
) : Receita(nome, ingredientes, tempoPreparo, modoPreparo) {
    override fun descricaoEquipamentos(): String {
        return "Esta receita requer o uso de uma batedeira."
    }
    constructor() : this("", emptyMap(), 0, "", 0)
}