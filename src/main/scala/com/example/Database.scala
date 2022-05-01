package com.example

import java.sql.DriverManager
import java.sql.Connection
import java.sql.SQLException
import java.sql.ResultSet
import javax.swing.{JPanel, JFrame, JLabel, BoxLayout, JList, JScrollPane}
import java.awt.{Dimension, Color, BorderLayout}

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

class DisplayTuple(primaryKeyName : String, primaryKey : String, relationName : String) extends JPanel {
    this.setPreferredSize(new Dimension(500,100))
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))
    def addEntry(attributes : List[String], format : List[String] => String, label : String) : Unit = {
        val res = Queries.queryDatabase("SELECT * FROM " + relationName + " WHERE (" + primaryKeyName + " = " + primaryKey + ");")
        res.next()
        val value = format((attributes.map(res.getString)))
        res.close()

        this.add(new JLabel(label + ": " + value))
    }

    def addEntry(attribute : String, format : (String) => String, label : String) : Unit = {
        val res = Queries.queryDatabase("SELECT * FROM " + relationName + " WHERE (" + primaryKeyName + " = " + primaryKey + ");")
        res.next()
        val value = format(res.getString(attribute))
        res.close()

        this.add(new JLabel(label + ": " + value))
    }
}

object Tester extends App {
    val frame = new JFrame()
    val otherPane = new JPanel()
    var test = Array[String]()
    Range(0,100).foreach((i : Int) => test = test :+ i.toString())
    val list = new JList(test)
    otherPane.add(new JScrollPane(list))
    val dt = new DisplayTuple("personne_id", "1", "persons")
    dt.addEntry(List("prenom", "nom"), (l: List[String]) => (l.map((s:String) => s + " ")).addString(new StringBuilder()).toString(), "Name")
    dt.addEntry("sexe", (s: String) => s, "Sexe")
    frame.add(dt, BorderLayout.EAST)
    frame.add(otherPane, BorderLayout.WEST)
    frame.remove(otherPane)
    frame.setVisible(true)
    frame.setSize(1000,1000)
}