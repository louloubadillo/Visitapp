package com.example.visitapp.activities

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.visitapp.R
import com.example.visitapp.data.DbInfo
import com.example.visitapp.databinding.ActivitySeleccionBinding
import com.example.visitapp.models.Departamento
import com.example.visitapp.models.Empleado
import com.example.visitapp.utilities.EmailSender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Seleccion : AppCompatActivity() {
    private lateinit var binding : ActivitySeleccionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion)

        binding = ActivitySeleccionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = DbInfo.departments?.let { CustomAdapter(it) };
        binding.rvDepartments.layoutManager = GridLayoutManager(this,2)
        binding.rvDepartments.adapter =adapter
        if (adapter != null) {
            adapter.notifyDataSetChanged()
        }

        Log.i("Seleccion","Binding working?")
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
        else{
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    public fun callMensajeCorreo(departamento: Int){

    }
}

class CustomAdapter(private val dataSet: Array<Departamento>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btn: Button

        init {
            // Define click listener for the ViewHolder's View.
            btn = view.findViewById(R.id.btn_Department)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.btn.context
        Log.i("Seleccion",dataSet[0].toString())
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.btn.text = dataSet[position].name
        viewHolder.btn.setOnClickListener {
            val encargado = DbInfo.employees?.get(dataSet[position].idx_encargado)
            CoroutineScope(Dispatchers.IO).launch {
                if (encargado != null) {
                    EmailSender.sendEmail(encargado.correo)
                }
            }

            val intent = Intent(context, MensajeCorreo::class.java)
            intent.putExtra("departamento", encargado?.departamento)
            //startActivityForResult(intent,1)
            (context as Activity).startActivityForResult(intent, 1)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}