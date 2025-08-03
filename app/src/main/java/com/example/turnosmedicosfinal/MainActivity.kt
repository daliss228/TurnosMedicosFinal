package com.example.turnosmedicosfinal

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.Toolbar

import android.content.Intent
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.example.turnosmedicosfinal.R
import com.example.turnosmedicosfinal.integration.Firebase


class MainActivity : AppCompatActivity() {

    private val repo = Firebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<FloatingActionButton>(R.id.fabCrearCita)
        fab.setOnClickListener {
            val intent = Intent(this, NuevaCitaActivity::class.java)
            startActivity(intent)
        }

        // Asignar toolbar como ActionBar
        val toolbar = findViewById<Toolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Mis turnos agendados"

        // Padding para insets (barras del sistema)
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(view.paddingLeft, statusBarHeight, view.paddingRight, view.paddingBottom)
            insets
        }

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
