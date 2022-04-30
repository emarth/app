package com.example

import java.sql.DriverManager
import java.sql.Connection
import java.sql.SQLException
import java.sql.ResultSet

object Queries {

    val url = "jdbc:postgresql://localhost:5432/appdb"
    val user = "postgres"
    val pass = "password"

    var conn: Connection = null
    try {
        conn = DriverManager.getConnection(url, user, pass)
    } catch {
        case e: SQLException => {e.printStackTrace()}
    }

    val queryDatabase = (query: String) => {
        val stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
        stm.executeQuery(query)
    }

    val updateDatabase = (queries : List[String]) => {
        val stm = conn.createStatement()
        queries.foreach((q: String) => {stm.addBatch(q)})
        stm.executeBatch()
    }

    val extractColumnStr = (res : ResultSet, colName : String) => {
        var arr = Array[String]()
        while(res.next()) {
            arr = arr :+ res.getString(colName)
        }
        arr
    }

    val extractColumnInt = (res : ResultSet, colName : String) => {
        var arr = Array[Int]()
        while(res.next()) {
            arr = arr :+ res.getInt(colName)
        }
        arr
    }

}