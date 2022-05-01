package com.example

import javax.swing.{JPanel, JLabel}
import javax.swing.JComboBox
import java.awt.BorderLayout
import javax.swing.JScrollPane
import javax.swing.JList
import java.awt.Dimension
import javax.swing.JButton
import java.awt.GridLayout
import java.awt.Color
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import java.sql.ResultSet

object ModeUtils {
    
    val getModes = (username : String) => {
        var res = Main.queries.queryDatabase(
        "select patient_id, prenom from (persons natural join patients)" +
        "where (username = \'" + username + "\');"
        )
        val patientModes = Main.queries.extractColumnStr(res, "prenom")
        res.beforeFirst()
        val patientIds = Main.queries.extractColumnInt(res, "patient_id")

        res = Main.queries.queryDatabase(
            "select employee_id, emploi_role from employees where (username = \'" +
            username + "\');"
        )

        val employeeModes = Main.queries.extractColumnStr(res, "emploi_role")
        res.beforeFirst()
        val employeeIds = Main.queries.extractColumnInt(res, "employee_id")
        
        var modes : Map[String, Int] = Map()

        Range(0, patientModes.length).foreach((i: Int) => {modes = modes + ("Patient: " + patientModes(i) -> patientIds(i))})
        Range(0, employeeModes.length).foreach((i: Int) => {modes = modes + ("[Employé(e)] Rôle: " + employeeModes(i) -> employeeIds(i))})
        modes 
    }

    val makeModePanel = (mode : String, id : Int) => {
        if (mode.slice(0,9) == "Patient: ") {
            new PatientMode(id.toString())
        } else if (mode == "[Employé(e)] Rôle: Secretaire"){
            new SecretaryMode()
        } else {
            new DentistMode(id.toString)
        }
    }

}


class SecretaryMode() extends JPanel {
    // create patient list
    val res  = Main.queries.queryDatabase("select * from (persons natural join patients) order by nom")
    val noms = Main.queries.extractColumnStr(res, "nom")
    res.beforeFirst()
    val prenoms = Main.queries.extractColumnStr(res, "prenom")
    res.beforeFirst()
    val patient_ids = Main.queries.extractColumnStr(res, "patient_id")
    res.close()

    this.setLayout(new BorderLayout())

    var patientListValues = Array[String]()
    Range(0, noms.length).foreach((i :Int) => {patientListValues = patientListValues :+ (noms(i) + ", " + prenoms(i))})
    val patientList = new JList(patientListValues)
    val scroll = new JScrollPane(patientList)
    scroll.setPreferredSize(new Dimension(150,600))
    this.add(scroll, BorderLayout.WEST)
    this.setPreferredSize(new Dimension(950,700))

     // displaypanel

    val dPanel = new JPanel()
    dPanel.setPreferredSize(new Dimension(800, 600))
    this.add(dPanel, BorderLayout.EAST)

    // create command panel
    val btnPanel = new JPanel()
    val viewBtn = new JButton("Voir information")
    viewBtn.addActionListener(new ActionListener() {
        def actionPerformed(x: ActionEvent): Unit = {
            val dt = new DisplayTuple("patient_id", patient_ids(patientList.getSelectedIndex()), "(persons natural join patients)")
            dt.addEntry(List("prenom", "nom"), (l: List[String]) => (l.map((s:String) => s + " ")).addString(new StringBuilder()).toString(), "Name")
            dt.addEntry("sexe", (s: String) => s, "Sexe")
            dt.addEntry("ssn", (s: String) => s, "SSN")
            dt.addEntry("dateNaissance", (s: String) => s, "Date de naissance")
            dt.addEntry("assurance", (s: String) => s, "Fournisseur d'assurance")
            dt.addEntry("numeroTel", (s: String) => s, "Numéro de téléphone")
            dt.addEntry("email", (s: String) => s, "Adresse e-mail")
            dt.addEntry("adresse_id", (s : String) => {
                val addr : ResultSet = Main.queries.queryDatabase("select * from adresses where ( adresse_id = " + s + ");")
                addr.next()
                val str = addr.getString("numero") + " " + addr.getString("rue") + ", " + addr.getString("ville") + ", " + addr.getString("province")
                addr.close()
                str
            }, "Adresse")
            dPanel.remove(content)
            content = dt
            dPanel.add(content)
            dPanel.updateUI()
        }
    })
    val editBtn = new JButton("Modifier information")
    val bookBtn = new JButton("Fixer rendez-vous")
    btnPanel.setLayout(new GridLayout(1,3))
    btnPanel.add(viewBtn); btnPanel.add(editBtn); btnPanel.add(bookBtn)

    // dpanel contents
    var content = new JPanel()

    this.add(btnPanel, BorderLayout.SOUTH)

    class BookPanel(patientID : String) extends JPanel {

    }
}

class PatientMode(patientID : String) extends JPanel {
    this.add(new JLabel("Patient mode under construction"))
}

class DentistMode(employeeID : String) extends JPanel {
    this.add(new JLabel("Dentist mode under construction"))
}