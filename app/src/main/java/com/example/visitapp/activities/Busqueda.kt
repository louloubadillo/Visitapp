package com.example.visitapp.activities

import android.R
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.visitapp.adapters.AutocompleteAdapter
import com.example.visitapp.models.Empleado
import com.example.visitapp.databinding.ActivityBusquedaBinding
import com.example.visitapp.utilities.EmailSender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Busqueda : AppCompatActivity() {
    private lateinit var binding: ActivityBusquedaBinding
    val mTAG = Busqueda::class.simpleName




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aquí se usa la base de datos
        // Este solo es un ejemplo
        val Empleados = arrayOf(
            Empleado("Jorge Flores", "IT","jorge@email.com"),
            Empleado("Enrique Ayala", "RH","enrique@email.com"),
            Empleado("Rodolfo Garza", "Operaciones","rodolfo@email.com"),
            Empleado("Luis Quintero", "Donaciones","luis@email.com"),
            Empleado("Alexis Barbaro", "IT","alexis@email.com"),
            Empleado("Mario Wong", "IT","mario@email.com"),
            Empleado("Karina Montemayor", "RH","karina@email.com"),
            Empleado("Eduardo Lopez", "Operaciones","eduardo@email.com")
        )

        val adapter = AutocompleteAdapter(this, R.layout.simple_list_item_1,Empleados)

        binding.autoCompleteTextViewEmpleado.threshold = 2
        binding.autoCompleteTextViewEmpleado.setAdapter(adapter)

        // Ocurre al seleccionar uno de los empleados en el autocompletado:
        binding.autoCompleteTextViewEmpleado.setOnItemClickListener{ parent, view,position,id ->

            var selected = parent.getItemAtPosition(position) as Empleado
            Toast.makeText(this,selected.correo, Toast.LENGTH_SHORT).show()
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    Log.i(mTAG, "Generating email session")
                    EmailSender.sendEmail("erick.gomez1003@gmail.com","Test","Lorem Ipsum")
                }
            }
        }

        // Intent de Mensaje de confirmación
        // Ocurre al oprimir el boton de Continuar
        binding.buttonContinuar.setOnClickListener {
            val intent = Intent(this, MensajeCorreo::class.java)
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