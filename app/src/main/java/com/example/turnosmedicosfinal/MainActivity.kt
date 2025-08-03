package com.example.turnosmedicosfinal

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.turnosmedicosfinal.R
import com.example.turnosmedicosfinal.integration.Firebase

class MainActivity : AppCompatActivity() {

    private val repo = Firebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mostrarMedicosEnConsola()
        mostrarTurnosEnConsola()
    }

    private fun mostrarMedicosEnConsola() {
        repo.obtenerMedicos(
            onSuccess = { medicos ->
                for (medico in medicos) {
                    Log.d("MEDICO", "Nombre: ${medico.nombre}, Especialidad: ${medico.especialidad}, Horarios: ${medico.horarios}, Foto: ${medico.foto}")
                }
            },
            onError = { error ->
                Log.e("MEDICO", "Error al obtener médicos: ${error.message}")
            }
        )
    }

    private fun mostrarTurnosEnConsola() {
        repo.obtenerTurnos(
            onSuccess = { turnos ->
                for (turno in turnos) {
                    Log.d("TURNO", "Paciente: ${turno.paciente}, Médico: ${turno.medico}, Horario: ${turno.horario}, Fecha: ${turno.fecha}")
                }
            },
            onError = { error ->
                Log.e("TURNO", "Error al obtener turnos: ${error.message}")
            }
        )
    }
}
