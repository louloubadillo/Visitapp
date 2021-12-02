package com.example.visitapp.models

data class Empleado(
    // Atributos de un Empleado
    val nombre: String,
    val departamento: String,
    val correo:  String
    )
    {
    override fun toString(): String {
        return nombre
    }
}
