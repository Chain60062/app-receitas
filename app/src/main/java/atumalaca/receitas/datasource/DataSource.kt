package atumalaca.receitas.datasource

import atumalaca.receitas.classes.Receita
import atumalaca.receitas.classes.ReceitaBatedeira
import atumalaca.receitas.classes.ReceitaForno
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow

class DataSource {
    private val db = FirebaseFirestore.getInstance()
    private val receitasCollection = db.collection("receitas2")
    private val _todasReceitas = MutableStateFlow<MutableList<Receita>>(mutableListOf())
    private val todasReceitas: StateFlow<MutableList<Receita>> = _todasReceitas

    // Criar nova receita
    fun salvarReceita(
        nome: String,
        ingredientes: Map<String, String>,
        tempoPreparo: Int,
        modoPreparo: String,
        tipo: String,
        tempoForno: Int? = null,
        temperatura: Int? = null,
        tempoBatendo: Int? = null
    ) {
        val data = mutableMapOf<String, Any>(
            "nome" to nome,
            "tempoPreparo" to tempoPreparo,
            "ingredientes" to ingredientes,
            "modoPreparo" to modoPreparo
        )

        when (tipo) {
            "forno" -> {
                data["tipo"] = "forno"
                if (tempoForno != null) data["tempoForno"] = tempoForno
                if (temperatura != null) data["temperatura"] = temperatura
            }

            "batedeira" -> {
                data["tipo"] = "batedeira"
                if (tempoBatendo != null) data["tempoBatendo"] = tempoBatendo
            }

            else -> data["tipo"] = "comum"
        }

        receitasCollection.add(data)
    }

    fun listarReceitas(): Flow<MutableList<Receita>> = callbackFlow {
        val listener = receitasCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val listaReceitas = snapshot.documents.mapNotNull { doc ->
                    val data = doc.data ?: return@mapNotNull null

                    val nome = data["nome"] as? String ?: ""
                    val tempoPreparo = (data["tempoPreparo"] as? Long)?.toInt() ?: 0
                    val modoPreparo = data["modoPreparo"] as? String ?: ""
                    @Suppress("UNCHECKED_CAST")
                    val ingredientes = data["ingredientes"] as? Map<String, String> ?: emptyMap()
                    val tipo = data["tipo"] as? String ?: "comum"

                    when (tipo.lowercase()) {
                        "forno" -> {
                            val tempoForno = (data["tempoForno"] as? Long)?.toInt() ?: 0
                            val temperatura = (data["temperatura"] as? Long)?.toInt() ?: 0

                            ReceitaForno(
                                nome = nome,
                                ingredientes = ingredientes,
                                tempoPreparo = tempoPreparo,
                                modoPreparo = modoPreparo,
                                tempoForno = tempoForno,
                                temperatura = temperatura
                            )
                        }
                        "batedeira" -> {
                            val tempoBatendo = (data["tempoBatendo"] as? Long)?.toInt() ?: 0

                            ReceitaBatedeira(
                                nome = nome,
                                ingredientes = ingredientes,
                                tempoPreparo = tempoPreparo,
                                modoPreparo = modoPreparo,
                                tempoBatendo = tempoBatendo
                            )
                        }
                        else -> {
                            Receita(
                                nome = nome,
                                ingredientes = ingredientes,
                                tempoPreparo = tempoPreparo,
                                modoPreparo = modoPreparo,
                                tipo = tipo
                            )
                        }
                    }
                }.toMutableList()
                trySend(listaReceitas)
            }
        }
        awaitClose { listener.remove() }
    }
}