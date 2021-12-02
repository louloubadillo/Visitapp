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
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.visitapp.adapters.AutocompleteAdapter
import com.example.visitapp.data.Credentials
import com.example.visitapp.models.Empleado
import com.example.visitapp.databinding.ActivityBusquedaBinding
import com.example.visitapp.utilities.EmailSender
import com.example.visitapp.utilities.RequestHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject


class Busqueda : AppCompatActivity() {
    private lateinit var binding: ActivityBusquedaBinding
    val mTAG = Busqueda::class.simpleName
    private val url = Credentials.API.URL



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val queue = RequestHandler.getInstance(this.applicationContext).requestQueue
        val req = JsonArrayRequest(Request.Method.GET, "$url/trabajadores",null, {
                response ->
            val jsonArray = response
            val Empleados = Array(jsonArray!!.length()) { Empleado("", "", "") }
            if (jsonArray != null) {
                val len = jsonArray.length()
                for (i in 0 until len) {
                    Log.i(mTAG, jsonArray[i].toString())
                    val jobj = jsonArray.getJSONObject(i)
                    val name = jobj.getString("nombre")
                    //val department = jobj.getString("departamento")
                    val email = jobj.getString("email")
                    Empleados[i] = Empleado(name,"",email);
                }
            }
            val adapter = AutocompleteAdapter(this, R.layout.simple_list_item_1,Empleados)

            binding.autoCompleteTextViewEmpleado.threshold = 2
            binding.autoCompleteTextViewEmpleado.setAdapter(adapter)

            // Ocurre al seleccionar uno de los empleados en el autocompletado:
            binding.autoCompleteTextViewEmpleado.setOnItemClickListener{ parent, view,position,id ->

                var selected = parent.getItemAtPosition(position) as Empleado
                //Toast.makeText(this,selected.correo, Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        Log.i(mTAG, selected.correo)
                        EmailSender.sendEmail(selected.correo,"Nueva visita","Un visitante necesita tu atención.")
                    }
                }
            }

            // Intent de Mensaje de confirmación
            // Ocurre al oprimir el boton de Continuar
            binding.buttonContinuar.setOnClickListener {
                val departamento = "0"
                val intent = Intent(this, MensajeCorreo::class.java)
                intent.putExtra("departamento",departamento)
                startActivityForResult(intent,1)
            }

        }, { Log.e(mTAG, it.toString())})
        RequestHandler.getInstance(this).addToRequestQueue(req)


        // Aquí se usa la base de datos
        // Este solo es un ejemplo
        /*val Empleados = arrayOf(
            Empleado("Jorge Flores", "IT","jorge@email.com"),
            Empleado("Enrique Ayala", "RH","enrique@email.com"),
            Empleado("Rodolfo Garza", "Operaciones","rodolfo@email.com"),
            Empleado("Luis Quintero", "Donaciones","luis@email.com"),
            Empleado("Alexis Barbaro", "IT","alexis@email.com"),
            Empleado("Mario Wong", "IT","mario@email.com"),
            Empleado("Karina Montemayor", "RH","karina@email.com"),
            Empleado("Eduardo Lopez", "Operaciones","eduardo@email.com")
        )*/


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