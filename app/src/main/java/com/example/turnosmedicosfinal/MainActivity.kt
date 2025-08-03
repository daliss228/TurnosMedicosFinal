package com.example.turnosmedicosfinal

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.Toolbar

import android.content.Intent
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

    }
}