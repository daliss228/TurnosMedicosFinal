package com.example.turnosmedicosfinal.integration

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.example.turnosmedicosfinal.models.Medico
import com.example.turnosmedicosfinal.models.Turno

class FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()

    // Leer todos los médicos y retornar una lista por callback
    fun obtenerMedicos(onSuccess: (List<Medico>) -> Unit, onError: (Exception) -> Unit) {
        db.collection("medicos")
            .get()
            .addOnSuccessListener { result ->
                val listaMedicos = result.map { it.toObject(Medico::class.java) }
                Log.d("FIREBASE", "Médicos cargados: $listaMedicos")
                onSuccess(listaMedicos)
            }
            .addOnFailureListener { exception ->
                Log.e("FIREBASE", "Error al obtener médicos", exception)
                onError(exception)
            }
    }

    // Crear un turno
    fun crearTurno(turno: Turno, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        db.collection("turnos")
            .add(turno)
            .addOnSuccessListener {
                Log.d("FIREBASE", "Turno guardado con éxito")
                onSuccess()
            }
            .addOnFailureListener { exception ->
                Log.e("FIREBASE", "Error al guardar turno", exception)
                onError(exception)
            }
    }

    // Leer todos los turnos
    fun obtenerTurnos(onSuccess: (List<Turno>) -> Unit, onError: (Exception) -> Unit) {
        db.collection("turnos")
            .get()
            .addOnSuccessListener { result ->
                val listaTurnos = result.map { it.toObject(Turno::class.java) }
                Log.d("FIREBASE", "Turnos cargados: $listaTurnos")
                onSuccess(listaTurnos)
            }
            .addOnFailureListener { exception ->
                Log.e("FIREBASE", "Error al obtener turnos", exception)
                onError(exception)
            }
    }
}