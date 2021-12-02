package com.example.visitapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.visitapp.databinding.ActivityMensajeCorreoBinding
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.visitapp.utilities.RequestHandler


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

        val queue = RequestHandler.getInstance(this.applicationContext).requestQueue
        RequestHandler.getInstance(this).addVisit(departamento)

        binding.btConfirm.setOnClickListener{
            val returnIntent = Intent()
            returnIntent.putExtra("result", 1)
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }
}