package com.example.visitapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.visitapp.databinding.ActivityMensajeCorreoBinding
import android.app.Activity




class MensajeCorreo : AppCompatActivity() {
    private lateinit var binding : ActivityMensajeCorreoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMensajeCorreoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btConfirm.setOnClickListener{
            val returnIntent = Intent()
            returnIntent.putExtra("result", 1)
            setResult(RESULT_OK, returnIntent)
            finish()
        }

    }
}