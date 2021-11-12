package com.example.visitapp.activities

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.visitapp.adapters.AutocompleteAdapter
import com.example.visitapp.models.Empleado
import com.example.visitapp.databinding.ActivityBusquedaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.mail.Message
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class Busqueda : AppCompatActivity() {
    private lateinit var binding: ActivityBusquedaBinding
    val mTAG = Busqueda::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aquí se usa la base de datos
        // Este solo es un ejemplo hardcodeado
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

        binding.autoCompleteTextViewEmpleado.setOnItemClickListener{ parent, view,position,id ->

            var selected = parent.getItemAtPosition(position) as Empleado

            Toast.makeText(this,selected.correo, Toast.LENGTH_SHORT).show()
        }

        // Hacer nueva activity de mensaje
        // A esta le vamos a pasar el string con el nombre del empleado
        binding.buttonContinuar.setOnClickListener {
            val intent = Intent(this, Mensaje::class.java)
            startActivity(intent)

        }
    }

    // Enviar correo dado un destinatario (to), un asunto (subject) y su contenido (content)
    private fun sendEmail(to: String, subject: String, content: String) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val mm = MimeMessage(MainActivity.mSession)
                    mm.addRecipient(
                        Message.RecipientType.TO,
                        InternetAddress(to)
                    )
                    mm.subject = subject
                    mm.setText(content)
                    Transport.send(mm)
                } catch (e : Exception) {
                    Log.d(mTAG, e.toString())
                }
            }
        }
    }
}