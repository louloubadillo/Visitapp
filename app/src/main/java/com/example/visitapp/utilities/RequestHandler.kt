package com.example.visitapp.utilities

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.android.volley.*
import com.android.volley.toolbox.*
import com.example.visitapp.data.Credentials
import com.example.visitapp.data.DbInfo
import com.example.visitapp.models.Empleado
import org.json.JSONArray
import org.json.JSONObject
import java.net.CookieHandler
import java.net.CookieManager
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


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
    fun populateEmployees() {
        val req = JsonArrayRequest(Request.Method.GET, "$url/departamentos", null,
            { response ->
                DbInfo.departments = Array(response!!.length()) { String() }
                val len = response.length()
                for (i in 0 until len) {
                    Log.i(mTAG, response[i].toString())
                    val jobj = response.getJSONObject(i)
                    val id = jobj.getString("id_departamento")
                    val name = jobj.getString("nombre")
                    val id_main = jobj.getString("id_encargado")
                    DbInfo.departments!![i] = name;
                }
            }, { Log.e(mTAG, it.toString())})
        addToRequestQueue(req)
    }
    fun populateDepartments() {
        val req = JsonArrayRequest(Request.Method.GET, "$url/trabajadores", null,
            { response ->
                DbInfo.employees = Array(response!!.length()) { Empleado("", "", "") }
                val len = response.length()
                for (i in 0 until len) {
                    Log.i(mTAG, response[i].toString())
                    val jobj = response.getJSONObject(i)
                    val name = jobj.getString("nombre")
                    val department = jobj.getString("departamento")
                    val email = jobj.getString("email")
                    DbInfo.employees!![i] = Empleado(name, department, email);
                }
            }, { Log.e(mTAG, it.toString())})
        addToRequestQueue(req)
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
    @RequiresApi(Build.VERSION_CODES.O)
    fun addVisit(id_dpt: String){
        val date = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault())
            .format(Instant.now())
        val req: StringRequest =
            object : StringRequest(Request.Method.POST, "$url/agregar-visita",
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    Log.i(mTAG, "Successfully added visit. Response is: ${response.substring(0, response.length)}")
                },
                Response.ErrorListener { Log.i(mTAG, "Could not add visit!!!") }) {
                override fun getParams(): MutableMap<String, String> {
                    val mutableMap: HashMap<String, String> = mutableMapOf(
                        "departamento" to id_dpt,
                        "fecha" to date
                    ) as HashMap<String, String>
                    Log.i(mTAG, "Attempting to add visit: $mutableMap")
                    return mutableMap
                }
            }
        addToRequestQueue(req)
    }
    fun login(username: String=Credentials.API.USER, password: String=Credentials.API.PASSWORD) {
        val manager = CookieManager()
        CookieHandler.setDefault(manager)
        // Login request to API
        val req: StringRequest =
            object : StringRequest(Request.Method.POST, "$url/login",
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    Log.i(mTAG, "Response is: ${response.substring(0, 500)}")
                },
                Response.ErrorListener { Log.i(mTAG, "Something went wrong when logging in!!!") }) {
                override fun getParams(): MutableMap<String, String> {
                    val mutableMap: HashMap<String, String> = mutableMapOf(
                        "user" to username,
                        "password" to password
                    ) as HashMap<String, String>
                    Log.i(mTAG, mutableMap.toString())
                    return mutableMap
                }
            }
        // Make request
        addToRequestQueue(req)
    }
}
