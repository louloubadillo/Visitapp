package com.example.visitapp.models

data class Departamento(
    val name: String,
    val idx_encargado: Int,
) {
    override fun toString(): String {
        return name
    }
}
