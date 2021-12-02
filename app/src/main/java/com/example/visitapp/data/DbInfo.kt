package com.example.visitapp.data

import com.example.visitapp.models.Departamento
import com.example.visitapp.models.Empleado

object DbInfo {
    var initialized: Boolean = false
    var employees: Array<Empleado>? = null
    var departments: Array<Departamento>? = null
}