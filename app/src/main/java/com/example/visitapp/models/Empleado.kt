package com.example.visitapp.models

data class Empleado(
    // Atributos de un Empleado
    val nombre: String,
    val departamento: String,
    val correo:  String
    )
    // En AutocompleteAdapter el usuario puede buscar
    // un empleado por su nombre o departamento, así
    // que envíamos a ambos
    {
    override fun toString(): String {
        return nombre
    }
}
