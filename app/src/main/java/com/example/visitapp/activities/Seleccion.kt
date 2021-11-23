package com.example.visitapp.activities

import android.app.Activity
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
            startActivityForResult(intent,1)

        }

        binding.dpto2.setOnClickListener{
            val intent = Intent(this, MensajeCorreo::class.java)
            startActivityForResult(intent,1)

        }

        binding.dpto3.setOnClickListener{
            val intent = Intent(this, MensajeCorreo::class.java)
            startActivityForResult(intent,1)

        }

        binding.dpto4.setOnClickListener{
            val intent = Intent(this, MensajeCorreo::class.java)
            startActivityForResult(intent,1)

        }
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