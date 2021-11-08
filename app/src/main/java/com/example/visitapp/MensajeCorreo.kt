package com.example.visitapp

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
        }

    }
}