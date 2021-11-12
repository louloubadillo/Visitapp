package com.example.visitapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.visitapp.R
import com.example.visitapp.databinding.ActivitySeleccionBinding

class Seleccion : AppCompatActivity() {
    private lateinit var binding : ActivitySeleccionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion)

        binding = ActivitySeleccionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dpto1.setOnClickListener{
            val intent = Intent(this, MensajeCorreo::class.java)
            startActivity(intent)

        }

        binding.dpto2.setOnClickListener{
            val intent = Intent(this, MensajeCorreo::class.java)
            startActivity(intent)

        }

        binding.dpto3.setOnClickListener{
            val intent = Intent(this, Mensaje::class.java)
            startActivity(intent)

        }

        binding.dpto4.setOnClickListener{
            val intent = Intent(this, Mensaje::class.java)
            startActivity(intent)

        }
    }
}