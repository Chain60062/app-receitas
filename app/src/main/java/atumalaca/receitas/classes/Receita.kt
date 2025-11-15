package atumalaca.receitas.classes


open class Receita(
    val nome: String = "",
    val ingredientes: Map<String, String> = emptyMap(),
    val tempoPreparo: Int = 0,
    val modoPreparo: String = "",
    val tipo: String = ""
) {
    //método polimórfico
    open fun descricaoEquipamentos(): String {
        return "Esta é uma receita geral sem equipamento específico."
    }
    //construtor vazio, teste do firestore
    constructor() : this("", emptyMap(), 0, "", "")
}
