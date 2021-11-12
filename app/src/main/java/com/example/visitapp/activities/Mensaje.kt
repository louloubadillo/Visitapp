package com.example.visitapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.visitapp.databinding.ActivityMensajeBinding

class Mensaje : AppCompatActivity() {

    private lateinit var binding : ActivityMensajeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMensajeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btConfirm.setOnClickListener{
            onBackPressed()
            // aqui, antes de esto se guardan los datos de la visita (hora de llegada y nombre de persona)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}