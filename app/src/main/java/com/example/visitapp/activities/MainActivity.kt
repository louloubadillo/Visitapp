package com.example.visitapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.visitapp.data.Credentials
import com.example.visitapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.mail.PasswordAuthentication
import javax.mail.Session

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var mSession : Session
    }

    private lateinit var mBinding : ActivityMainBinding
    private val mTAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                initializeMailSession()
            }
        }

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

    private fun initializeMailSession() {
        try {
            val props = System.getProperties()
            props["mail.smtp.host"] = "smtp.gmail.com"
            props["mail.smtp.socketFactory.port"] = "465"
            props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.port"] = "465"

            mSession = Session.getInstance(props,
                object: javax.mail.Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(
                            Credentials.GmailLogin.EMAIL,
                            Credentials.GmailLogin.PASSWORD
                        )
                    }
                })
            Log.i(mTAG, mSession.toString())
        } catch (e : Exception) {
            Log.d(mTAG, e.toString())
        }
    }
}