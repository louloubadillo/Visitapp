package com.example.visitapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.visitapp.databinding.ActivityMensajeCorreoBinding

class MensajeCorreo : AppCompatActivity() {
    private lateinit var binding : ActivityMensajeCorreoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMensajeCorreoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btConfirm.setOnClickListener{
            onBackPressed()
            // aqui, antes de volver a la pantalla principal se guardan los datos de la visita (hora de llegada y dpto)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}