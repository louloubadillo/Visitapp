package com.example.visitapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.visitapp.databinding.ActivityMensajeCorreoBinding
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.visitapp.utilities.RequestHandler
import java.text.DateFormat
import java.text.SimpleDateFormat
import kotlin.String as String1


class MensajeCorreo : AppCompatActivity() {
    private lateinit var binding : ActivityMensajeCorreoBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMensajeCorreoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var departamento = ""
        val extras = getIntent().getExtras()
        if(extras != null){
             departamento = extras.getString("departamento").toString()
        }

        val calendar = Calendar.getInstance()
        val currentdate = DateFormat.getDateInstance().format(calendar.time)
        // Dec 2, 2021
        // MMM d, yyyy"
        val formatter = SimpleDateFormat("MMM d, yyyy");
        val inputPattern = "EE MMM dd HH:mm:ss z yyyy"
        val outputPattern = "yyyy-MM-dd HH:mm:ss"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)

            val date = inputFormat.parse(calendar.time.toString())
            val str = outputFormat.format(date)

        // Sat Jun 01 12:53:10 IST 2013
        //val date = formatter.parse(calendar.time.toString())

        //val sqlDate = java.sql.Date(date.getTime())
        //MYSQL quiere yyyy-MM-dd HH:mm:ss

        //TODO: Cambiar el departamento predeterminado por el que corresponde
        val queue = RequestHandler.getInstance(this.applicationContext).requestQueue
        RequestHandler.getInstance(this).addVisit(str, departamento)

        binding.btConfirm.setOnClickListener{
            val returnIntent = Intent()
            returnIntent.putExtra("result", 1)
            setResult(RESULT_OK, returnIntent)
            finish()
        }

    }
}