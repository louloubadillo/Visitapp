package com.example.visitapp.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import com.example.visitapp.models.Empleado
import java.util.*

// Clase para autocompletar al buscar un empleado escribiendo su nombre o departamento
class AutocompleteAdapter (context: Context, val layoutResource: Int, val empleados: Array<Empleado>) : ArrayAdapter<Empleado>(context,layoutResource,empleados){

    var filteredEmpleados: List<Empleado> = listOf()

    override fun getCount() : Int = filteredEmpleados.size

    override fun getItem(position: Int): Empleado = filteredEmpleados[position]

/*    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutResource, parent, false)

        view.textView

    }*/


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                filteredEmpleados = filterResults.values as List<Empleado>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                val queryString = charSequence?.toString()?.lowercase(Locale.getDefault())

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    empleados.asList()
                else
                    empleados.filter {
                        it.nombre.lowercase(Locale.getDefault()).contains(queryString) || it.correo.lowercase().contains(queryString)
                    }
                return filterResults
            }
        }
    }




}