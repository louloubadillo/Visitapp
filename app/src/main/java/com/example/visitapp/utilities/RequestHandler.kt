package com.example.visitapp.utilities

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.visitapp.data.Credentials
import org.json.JSONObject

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
    fun login(username: String=Credentials.API.USER, password: String=Credentials.API.PASSWORD) {
        var credentials = JSONObject()
        credentials.put("user", username)
        credentials.put("password", password)
        val req = JsonObjectRequest(Request.Method.POST, "$url/login", credentials,
            {
                response -> Log.d(mTAG, response.toString())
            },
            {
                Log.e(mTAG, it.toString())
            }
        )
        addToRequestQueue(req)
    }
}