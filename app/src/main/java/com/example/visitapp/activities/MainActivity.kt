package com.example.visitapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.visitapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btPerson.setOnClickListener{
            val intent = Intent(this, Busqueda::class.java)
            startActivity(intent)
        }

        binding.btDepartment.setOnClickListener{
            val intent = Intent(this, Seleccion::class.java)
            startActivity(intent)
        }

        binding.btHelp.setOnClickListener{
            val intent = Intent(this, Mensaje::class.java)
            startActivity(intent)

        }


    }
}