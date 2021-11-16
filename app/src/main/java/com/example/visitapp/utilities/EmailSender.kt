package com.example.visitapp.utilities

import android.util.Log
import com.example.visitapp.data.Credentials
import java.lang.Exception
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailSender {
    companion object {
        private val mTAG = EmailSender::class.simpleName
        lateinit var mSession : Session
        fun initializeMailSession() {
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
        // Enviar correo dado un destinatario (to), un asunto (subject) y su contenido (content)
        fun sendEmail(to: String, subject: String, content: String) {
            try {
                val mm = MimeMessage(mSession)
                mm.addRecipient(
                    Message.RecipientType.TO,
                    InternetAddress(to)
                )
                mm.subject = subject
                mm.setText(content)
                Transport.send(mm)
            } catch (e : Exception) {
                Log.d(mTAG, e.toString())
            }
        }
    }
}