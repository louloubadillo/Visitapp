package com.example.visitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.visitapp.databinding.ActivityMainBinding
import android.R
import android.app.Dialog
import android.graphics.Color
import androidx.fragment.app.FragmentTransaction
import android.graphics.drawable.ColorDrawable

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btDepartment.setOnClickListener{
            val intent = Intent(this, Busqueda::class.java)
            startActivity(intent)
        }

        binding.btPerson.setOnClickListener{
            val intent = Intent(this, Seleccion::class.java)
            startActivity(intent)
        }

        binding.btHelp.setOnClickListener{
            val intent = Intent(this, Mensaje::class.java)
            startActivity(intent)

        }


    }
}