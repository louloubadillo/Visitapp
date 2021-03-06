package com.example.visitapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.visitapp.data.DbInfo
import com.example.visitapp.databinding.ActivityMainBinding
import com.example.visitapp.utilities.DbUtils
import com.example.visitapp.utilities.EmailSender
import com.example.visitapp.utilities.RequestHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityMainBinding
    private val mTAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                Log.i(mTAG, "Generating email session")
                EmailSender.initializeMailSession()
                if (!DbInfo.initialized) {
                    initializeDb()
                }
            }
        }

        // Intents a Busqueda, Selección y Mensaje, respectivamente, en los botones correspondientes.
        mBinding.btPerson.setOnClickListener{
            val intent = Intent(this, Busqueda::class.java)
            startActivity(intent)
        }

        mBinding.btDepartment.setOnClickListener{
            val intent = Intent(this, Seleccion::class.java)
            startActivity(intent)
        }

        mBinding.btHelp.setOnClickListener{
            val intent = Intent(this, Mensaje::class.java)
            startActivity(intent)
        }
    }
    suspend fun initializeDb() {
        val queue = RequestHandler.getInstance(this.applicationContext).requestQueue
        RequestHandler.getInstance(this).login()
        delay(5000L)
        RequestHandler.getInstance(this).populateEmployees()
        RequestHandler.getInstance(this).populateDepartments()
        DbInfo.initialized = true
    }
}