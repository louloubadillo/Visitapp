package com.example.visitapp.utilities

import android.os.StrictMode
import android.util.Log
import com.example.visitapp.data.Credentials
import java.sql.*
import java.util.Properties

object DbUtils {
    private val mTAG = DbUtils::class.simpleName
    internal var conn: Connection? = null

    fun connect() {
        Log.d(mTAG, "HERE")
//        val connectionProps = Properties()
//        connectionProps.put("user", "root")
//        connectionProps.put("password", "huntingAnt@26")
        try {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.68.106:3306",// +
//                "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT",
//                "jdbc:" + Credentials.DbConnection.DATABASE + "://" +
//                        Credentials.DbConnection.HOST +
//                        ":" + Credentials.DbConnection.PORT + "/" +
//                        Credentials.DbConnection.NAME,
//                connectionProps
            "kotlin", "something"
            )
            Log.d(mTAG, "Success!")
        } catch (ex: SQLException) {
            Log.e(mTAG, "SQL: $ex, ${ex.sqlState}")
            ex.printStackTrace()
        } catch (ex: Exception) {
            Log.e(mTAG, "Normal: $ex")
            ex.printStackTrace()
        }
    }

    fun test() {
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT * FROM DEPARTAMENTOS;")
            if (stmt.execute("SELECT * FROM DEPARTAMENTOS;")) {
                resultset = stmt.resultSet
            }
            while (resultset!!.next()) {
                Log.d(mTAG, resultset.getString("nombre"))
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }
}