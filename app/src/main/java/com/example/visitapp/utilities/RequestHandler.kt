package com.example.visitapp.utilities

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.*
import com.example.visitapp.data.Credentials
import org.json.JSONObject
import java.net.CookieHandler
import java.net.CookieManager


class RequestHandler constructor(context: Context) {

    private val mTAG = RequestHandler::class.simpleName
    private val url = Credentials.API.URL
    companion object {

        @Volatile
        private var INSTANCE: RequestHandler? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RequestHandler(context).also {
                    INSTANCE = it
                }
            }
    }
    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
    fun getWorkers(){
        val req = StringRequest(Request.Method.GET, "$url/trabajadores", {
            response ->  Log.i(mTAG,"Response is: ${response.substring(0,500)}")
        }, { Log.i(mTAG, "Can't do")})
        addToRequestQueue(req)
    }
    fun getDepartments(){
        val req = StringRequest(Request.Method.GET, "$url/departamentos", {
                response ->  Log.i(mTAG,"Response is: ${response.substring(0,500)}")
        }, { Log.i(mTAG, "Can't do")})
        addToRequestQueue(req)
    }
    fun addVisit(fecha: String, departamento:String){
        Log.i(mTAG,"Attemping to add visit")
        Log.i(mTAG,fecha.toString())
        val req: StringRequest =
            object : StringRequest(Request.Method.POST, "$url/agregar-visita", Response.Listener<String>
            { response ->
                // Display the first 500 characters of the response string.
                Log.i(mTAG, "yayResponse is: ${response.substring(0, response.length)}")
            },
                Response.ErrorListener { Log.i(mTAG, "Didnt work") }) {

                override fun getParams(): MutableMap<String, String> {
                    val mutableMap: HashMap<String, String> = mutableMapOf(
                        "departamento" to departamento,
                        "fecha" to fecha
                    ) as HashMap<String, String>
                    return mutableMap
                }

            }
        addToRequestQueue(req)
    }
    fun login(username: String=Credentials.API.USER, password: String=Credentials.API.PASSWORD) {
        val manager = CookieManager()
        CookieHandler.setDefault(manager)
        var credentials = JSONObject()
        credentials.put("user", username)

        credentials.put("password", password)
        Log.i(mTAG, credentials.toString())
        /*
        val req = JsonObjectRequest(Request.Method.POST, "$url/login", credentials,
            {

                response -> Log.d(mTAG, response.toString())
            },
            {
                Log.e(mTAG, it.toString())
            }
        )
        */
        //here goes nothing
        val req: StringRequest =
            object : StringRequest(Request.Method.POST, "$url/login", Response.Listener<String>
            { response ->
                // Display the first 500 characters of the response string.
                Log.i(mTAG, "Response is: ${response.substring(0, 500)}")
            },
                Response.ErrorListener { Log.i(mTAG, "Didnt work") }) {

                override fun getParams(): MutableMap<String, String> {
                    val mutableMap: HashMap<String, String> = mutableMapOf(
                        "user" to username,
                        "password" to password
                    ) as HashMap<String, String>
                    return mutableMap
                }

            }

        addToRequestQueue(req)
    }
}
