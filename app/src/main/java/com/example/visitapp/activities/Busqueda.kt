package com.example.visitapp.activities

import android.R
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.visitapp.adapters.AutocompleteAdapter
import com.example.visitapp.data.Credentials
import com.example.visitapp.data.DbInfo
import com.example.visitapp.models.Empleado
import com.example.visitapp.databinding.ActivityBusquedaBinding
import com.example.visitapp.utilities.EmailSender
import com.example.visitapp.utilities.RequestHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Busqueda : AppCompatActivity() {
    private lateinit var binding: ActivityBusquedaBinding
    val mTAG = Busqueda::class.simpleName
    private val url = Credentials.API.URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i(mTAG, DbInfo.employees.toString())
        var selected: Empleado? = null
        val adapter = AutocompleteAdapter(this, R.layout.simple_list_item_1, DbInfo.employees!!)

        binding.autoCompleteTextViewEmpleado.threshold = 2
        binding.autoCompleteTextViewEmpleado.setAdapter(adapter)

        // Ocurre al seleccionar uno de los empleados en el autocompletado:
        binding.autoCompleteTextViewEmpleado.setOnItemClickListener{ parent, view, position, id ->
            selected = parent.getItemAtPosition(position) as Empleado
        }

        // Intent de Mensaje de confirmación
        // Ocurre al oprimir el boton de Continuar
        binding.buttonContinuar.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    Log.i(mTAG, selected!!.correo)
                    EmailSender.sendEmail(selected!!.correo)
                }
            }
            val intent = Intent(this, MensajeCorreo::class.java)
            intent.putExtra("departamento", selected!!.departamento)
            startActivityForResult(intent,1)
        }
    }

    // Handler para cuando se cierra el intent de MensajeCorreo, de tal forma que vuelve a MainActivity sin acumularse en el stack.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 -> {
                    if (data != null) {
                        if(data.getIntExtra("result",0) == 1)
                            finish()
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}