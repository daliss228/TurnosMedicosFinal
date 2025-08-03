package com.example.turnosmedicosfinal.models

data class Turno(
    val paciente: String = "",
    val medico: String = "",
    val horario: String = "",
    val fecha: String = "",
    var especialidad: String = ""
)
