package atumalaca.receitas.datasource

import atumalaca.receitas.classes.Receita
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


    // Listar todas as receitas
//    fun listarReceitas(): Flow<MutableList<Receita>> {
//        val listaReceitas: MutableList<Receita> = mutableListOf()
//
//        receitasCollection.get().addOnCompleteListener { querySnapshot ->
//            if (querySnapshot.isSuccessful) {
//                for (document in querySnapshot.result) {
//                    val receita = document.toObject(Receita::class.java)
//                    listaReceitas.add(receita)
//                    _todasReceitas.value = listaReceitas
//                }
//            }
//        }
//        return todasReceitas
//    }
    // DataSource.kt
    fun listarReceitas(): Flow<MutableList<Receita>> = callbackFlow {
        val listener = receitasCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val listaReceitas = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Receita::class.java)
                }.toMutableList()
                trySend(listaReceitas)
            }
        }

        awaitClose { listener.remove() }
    }


    // Remover uma receita
    fun removerReceita(docId: String) {
        receitasCollection.document(docId).delete()
    }
}