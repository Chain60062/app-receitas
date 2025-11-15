package atumalaca.receitas.classes


open class Receita(
    val nome: String = "",
    val ingredientes: Map<String, String> = emptyMap(),
    val tempoPreparo: Int = 0,
    val modoPreparo: String = "",
    var tipo: String = ""
) {
    //construtor vazio, teste do firestore
    constructor() : this("", emptyMap(), 0, "")
}
