package com.example.turnosmedicosfinal.models

data class MedicoResponse(
    val documents: List<MedicoDocument>
)

data class MedicoDocument(
    val fields: MedicoFields
)

data class MedicoFields(
    val nombre: FirebaseField,
    val especialidad: FirebaseField,
    val horarios: FirebaseArrayField
)

data class FirebaseField(
    val stringValue: String
)

data class FirebaseArrayField(
    val arrayValue: ArrayValues
)

data class ArrayValues(
    val values: List<FirebaseField>
)
