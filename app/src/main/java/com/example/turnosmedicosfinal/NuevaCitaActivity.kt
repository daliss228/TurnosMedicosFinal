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
import java.text.SimpleDateFormat
import java.util.*

class NuevaCitaActivity : AppCompatActivity() {

    private lateinit var fechaTextView: TextView
    private lateinit var editTextNombre: EditText
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_cita)

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
        val spinnerEspecialistas = findViewById<Spinner>(R.id.spinnerEspecialistas)
        val spinnerHorario = findViewById<Spinner>(R.id.spinnerHorario)
        val btnCrearCita = findViewById<Button>(R.id.btnCrearCita)
        fechaTextView = findViewById(R.id.textFecha)
        editTextNombre = findViewById(R.id.editTextNombre)

        // Datos
        val especialistas = listOf("Selecciona un especialista", "Cardiólogo", "Pediatra", "Dermatólogo")
        val horarios = listOf("Selecciona un horario", "09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "15:00 - 16:00")

        spinnerEspecialistas.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, especialistas)
        spinnerHorario.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, horarios)

        // Validación
        fun verificarCampos() {
            val especialistaOk = spinnerEspecialistas.selectedItemPosition > 0
            val fechaOk = fechaTextView.text.toString() != "Toca para elegir fecha"
            val horarioOk = spinnerHorario.selectedItemPosition > 0
            val nombreOk = editTextNombre.text.toString().trim().isNotEmpty()
            btnCrearCita.isEnabled = especialistaOk && fechaOk && horarioOk && nombreOk
        }

        spinnerEspecialistas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                verificarCampos()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerHorario.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                verificarCampos()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        editTextNombre.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                verificarCampos()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

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
            val mensaje = """
                ✅ Cita creada:
                Especialista: ${spinnerEspecialistas.selectedItem}
                Fecha: ${fechaTextView.text}
                Hora: ${spinnerHorario.selectedItem}
                Nombre: ${editTextNombre.text}
            """.trimIndent()

            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
