package atumalaca.receitas.classes


open class Receita(
    var nome: String = "",
    var ingredientes: Map<String, String> = emptyMap(),
    var tempoPreparo: Int = 0,
    var modoPreparo: String = ""
) {
    //construtor vazio
    constructor() : this("", emptyMap(), 0, "")
}
