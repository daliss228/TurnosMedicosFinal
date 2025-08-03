package com.example.turnosmedicosfinal.models

data class Medico(
    val nombre: String = "",
    val especialidad: String = "",
    val horarios: List<String> = emptyList(),
    val foto: String = ""
)
