package atumalaca.receitas.classes

open class Receita(
    val nome: String,
    val ingredientes: Map<String, String>, //ingredientes e suas medidas(ambas strings)
    val tempoPreparo: Int, // em minutos
    val modoPreparo: String
) {
    open fun calcularDificuldade(): String {
        return when {
            tempoPreparo < 15 -> "Fácil"
            tempoPreparo < 40 -> "Médio"
            else -> "Difícil"
        }
    }
}
