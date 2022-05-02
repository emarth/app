package com.example

import java.sql.DriverManager
import java.sql.Connection
import java.sql.SQLException
import java.sql.ResultSet
import javax.swing.{JPanel, JFrame, JLabel, BoxLayout, JList, JScrollPane}
import java.awt.{Dimension, Color, BorderLayout}
import javax.swing.JTextField
import javax.swing.JButton
import javax.swing.JComboBox
import java.awt.event.ActionListener
import java.awt.event.ActionEvent

class Queries(user : String, pass : String) {

    val url = "jdbc:postgresql://localhost:5432/appdb"

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
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))
    def addEntry(attributes : List[String], format : List[String] => String, label : String) : Unit = {
        val res = Main.queries.queryDatabase("SELECT * FROM " + relationName + " WHERE (" + primaryKeyName + " = " + primaryKey + ");")
        res.next()
        val value = format((attributes.map(res.getString)))
        res.close()

        this.add(new JLabel(label + ": " + value))
    }

    def addEntry(attribute : String, format : (String) => String, label : String) : Unit = {
        val res = Main.queries.queryDatabase("SELECT * FROM " + relationName + " WHERE (" + primaryKeyName + " = " + primaryKey + ");")
        res.next()
        val value = format(res.getString(attribute))
        res.close()

        this.add(new JLabel(label + ": " + value))
    }
}

class EditPanel(primaryKeyName : String, primaryKey : String, relationName : String) extends JPanel {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))
    var entryFs = List[EntryField]()
    val submit = new JButton("Update")
    val conferror = new JLabel()
    submit.addActionListener(new ActionListener() {
        def actionPerformed(x: ActionEvent): Unit = {
            try {
            Main.queries.updateDatabase(entryFs.map((ef : EntryField) => {
                "UPDATE " + relationName + " " + ef.getQueryFragment() + "WHERE (" + primaryKeyName + " = " + primaryKey + ");"
            }))
            conferror.setText("Succès!")
        } catch {
            case e: SQLException => {conferror.setText("Données Invalides."); e.printStackTrace()}
        }
        }
    })
    this.add(submit)
    this.add(conferror)

    
    def addEntryText(attribute : String, queryFormat : (String) => String, label : String, format : (String) => String) : Unit = {
        val et = new EntryText(attribute, queryFormat, label, format, true)
        entryFs = entryFs :+ et
        this.add(et)
    }

    def addEmptyEntryText(attribute : String, queryFormat : (String) => String, label : String, format : (String) => String) : Unit = {
        val et = new EntryText(attribute, queryFormat, label, format, false)
        entryFs = entryFs :+ et
        this.add(et)
    }

    def addEntrySelect(attribute : String, queryFormat : (String) => String, label : String,
    choices : Array[String], choicesVal : Array[String]) : Unit = {
        val et = new EntrySelect(attribute, queryFormat, label, choices, choicesVal)
        entryFs = entryFs :+ et
        this.add(et)
    }

    // trait + subclasses

    trait EntryField extends JPanel {
        def getQueryFragment() : String
    }

    class EntryText(attribute : String, queryFormat : (String) => String, label : String, format : (String) => String, dfText : Boolean) extends EntryField {
        val jlb = new JLabel(label + ": ")
        var defaultText = ""
        if (dfText) {
            val res = Main.queries.queryDatabase("SELECT * FROM " + relationName + " WHERE (" + primaryKeyName + " = " + primaryKey + ");")
            res.next()
            defaultText = format(res.getString(attribute))
            res.close()
        }

        val txtF = new JTextField(defaultText)
        txtF.setPreferredSize(new Dimension(100,30))
        this.add(jlb)
        this.add(txtF)

        def getQueryFragment() : String = {
            "SET " + attribute + " = " + queryFormat(txtF.getText()) + " "
        }
    }

    class EntrySelect(attribute : String, queryFormat : (String) => String, label : String,
    choices : Array[String], choicesVal : Array[String]) extends EntryField {
        val jlb = new JLabel(label + ": ")
        val sel = new JComboBox[String](choices)
        this.add(jlb)
        this.add(sel)

        def getQueryFragment() : String = {
            "SET " + attribute + " = " + queryFormat(choicesVal(sel.getSelectedIndex())) + " "
        }
    } 
}