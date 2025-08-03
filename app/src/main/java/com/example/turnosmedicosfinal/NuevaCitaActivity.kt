package com.example.turnosmedicosfinal

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.turnosmedicosfinal.integration.Firebase
import com.example.turnosmedicosfinal.models.Medico
import com.example.turnosmedicosfinal.models.Turno
import java.text.SimpleDateFormat
import java.util.*

class NuevaCitaActivity : AppCompatActivity() {

    private val repo = Firebase()
    private lateinit var listaMedicos: List<Medico>

    private lateinit var spinnerEspecialistas: Spinner
    private lateinit var spinnerHorario: Spinner
    private lateinit var btnCrearCita: Button
    private lateinit var fechaTextView: TextView
    private lateinit var editTextNombre: EditText

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_cita)

        cargarMedicos()

        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarNuevaCita)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Crear nuevo turno"
        toolbar.setNavigationOnClickListener { finish() }

        // Evitar superposición con la barra de estado
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(view.paddingLeft, statusBarHeight, view.paddingRight, view.paddingBottom)
            insets
        }

        // Inicializar vistas
        spinnerEspecialistas = findViewById(R.id.spinnerEspecialistas)
        spinnerHorario = findViewById(R.id.spinnerHorario)
        btnCrearCita = findViewById(R.id.btnCrearCita)
        fechaTextView = findViewById(R.id.textFecha)
        editTextNombre = findViewById(R.id.editTextNombre)

        // TextWatcher para nombre
        editTextNombre.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = verificarCampos()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Selección de fecha
        fechaTextView.setOnClickListener {
            val dp = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    fechaTextView.text = formato.format(calendar.time)
                    verificarCampos()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dp.datePicker.minDate = System.currentTimeMillis()
            dp.show()
        }

        // Acción del botón
        btnCrearCita.setOnClickListener {
            val nombrePaciente = editTextNombre.text.toString().trim()
            val fechaSeleccionada = fechaTextView.text.toString()
            val horarioSeleccionado = spinnerHorario.selectedItem.toString()

            // Obtener el médico seleccionado (restando 1 porque hay un "Selecciona...")
            val posicionMedico = spinnerEspecialistas.selectedItemPosition - 1
            val medicoSeleccionado = if (posicionMedico >= 0) listaMedicos[posicionMedico] else null

            val nuevoTurno = Turno(
                paciente = nombrePaciente,
                medico = medicoSeleccionado?.nombre ?: "",
                especialidad = medicoSeleccionado?.especialidad ?: "",
                fecha = fechaSeleccionada,
                horario = horarioSeleccionado
            )
            repo.crearTurno(
                turno = nuevoTurno,
                onSuccess = {
                    Toast.makeText(this, "✅ Turno guardado con éxito", Toast.LENGTH_LONG).show()
                    finish() // Cierra la actividad
                },
                onError = { error ->
                    Toast.makeText(this, "❌ Error al guardar turno: ${error.message}", Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    private fun cargarMedicos() {
        repo.obtenerMedicos(
            onSuccess = { medicos ->
                listaMedicos = medicos
                val nombresMedicos = mutableListOf("Selecciona un especialista")
                nombresMedicos.addAll(medicos.map { it.nombre })

                spinnerEspecialistas.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    nombresMedicos
                )

                spinnerEspecialistas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        if (position > 0) {
                            val medicoSeleccionado = listaMedicos[position - 1] // -1 por "Selecciona..."
                            actualizarHorarios(medicoSeleccionado.horarios)
                        } else {
                            actualizarHorarios(emptyList())
                        }
                        verificarCampos()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            },
            onError = {
                Toast.makeText(this, "Error al cargar médicos", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun actualizarHorarios(horarios: List<String>) {
        val horariosDisponibles = mutableListOf("Selecciona un horario")
        horariosDisponibles.addAll(horarios)

        spinnerHorario.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            horariosDisponibles
        )

        spinnerHorario.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                verificarCampos()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun verificarCampos() {
        val especialistaOk = spinnerEspecialistas.selectedItemPosition > 0
        val fechaOk = fechaTextView.text.toString() != "Toca para elegir fecha"
        val horarioOk = spinnerHorario.selectedItemPosition > 0
        val nombreOk = editTextNombre.text.toString().trim().isNotEmpty()
        btnCrearCita.isEnabled = especialistaOk && fechaOk && horarioOk && nombreOk
    }
}
