package com.example.turnosmedicosfinal

import TurnoAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.Toolbar

import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.example.turnosmedicosfinal.R
import com.example.turnosmedicosfinal.integration.Firebase
import com.example.turnosmedicosfinal.models.Turno


class MainActivity : AppCompatActivity() {

    private val repo = Firebase()
    private lateinit var recyclerView: RecyclerView
    private lateinit var turnoAdapter: TurnoAdapter
    private var listaTurnos = mutableListOf<Turno>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerTurnos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        turnoAdapter = TurnoAdapter(listaTurnos)
        recyclerView.adapter = turnoAdapter

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

    override fun onResume() {
        super.onResume()
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
                listaTurnos.clear()
                listaTurnos.addAll(turnos)
                turnoAdapter.notifyDataSetChanged()  // Refrescar RecyclerView

                // Opcional: también mostrar en Log
                for (turno in turnos) {
                    Log.d("TURNO", "Paciente: ${turno.paciente}, Médico: ${turno.medico}, Horario: ${turno.horario}, Fecha: ${turno.fecha}, Esp: ${turno.especialidad}")
                }
            },
            onError = { error ->
                Log.e("TURNO", "Error al obtener turnos: ${error.message}")
                Toast.makeText(this, "Error al cargar turnos", Toast.LENGTH_SHORT).show()
            }
        )
    }

}