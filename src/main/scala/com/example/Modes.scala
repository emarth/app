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
import javax.swing.border.Border

object ModeUtils {
    
    val getModes = (username : String) => {
        var res = Main.queries.queryDatabase(
        "select patient_id, prenom from (dentist.persons natural join dentist.patients)" +
        "where (username = \'" + username + "\');"
        )
        val patientModes = Main.queries.extractColumnStr(res, "prenom")
        res.beforeFirst()
        val patientIds = Main.queries.extractColumnInt(res, "patient_id")

        res = Main.queries.queryDatabase(
            "select employee_id, emploi_role from dentist.employees where (username = \'" +
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
    val res  = Main.queries.queryDatabase("select * from (dentist.persons natural join dentist.patients) order by nom")
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
            if (patientList.getSelectedIndex() != -1) {
            val dt = new DisplayTuple("patient_id", patient_ids(patientList.getSelectedIndex()), "(dentist.persons natural join dentist.patients)")
            dt.addEntry(List("prenom", "nom"), (l: List[String]) => (l.map((s:String) => s + " ")).addString(new StringBuilder()).toString(), "Name")
            dt.addEntry("sexe", (s: String) => s, "Sexe")
            dt.addEntry("ssn", (s: String) => s, "SSN")
            dt.addEntry("dateNaissance", (s: String) => s, "Date de naissance")
            dt.addEntry("assurance", (s: String) => s, "Fournisseur d'assurance")
            dt.addEntry("numeroTel", (s: String) => s, "Numéro de téléphone")
            dt.addEntry("email", (s: String) => s, "Adresse e-mail")
            dt.addEntry("adresse_id", (s : String) => {
                val addr : ResultSet = Main.queries.queryDatabase("select * from dentist.adresses where ( adresse_id = " + s + ");")
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
        }
    })
    val editBtn = new JButton("Modifier information")
    editBtn.addActionListener(new ActionListener() {
        def actionPerformed(x: ActionEvent): Unit = {
            if (patientList.getSelectedIndex() != -1) {
            // get personne_id
            val patID = patient_ids(patientList.getSelectedIndex())
            var rs = Main.queries.queryDatabase(
            "SELECT personne_id FROM (Dentist.Persons NATURAL JOIN Dentist.Patients) WHERE (patient_id = " + patID + ");"
            )
            rs.next(); val perID = rs.getString("personne_id"); rs.close()
            
            // create entry panel
            val ep = new EditPanel("personne_id", perID, "Dentist.Persons")
            ep.addEntryText("prenom", (s: String) => "\'" + s + "\'", "Prénom", (s: String) => s)
            ep.addEntryText("nom", (s: String) => "\'" + s + "\'", "Nom", (s: String) => s)
            ep.addEntryText("sexe", (s: String) => "\'" + s + "\'", "Sexe", (s: String) => s)
            ep.addEntryText("ssn", (s: String) =>  s, "SSN", (s: String) => s)
            ep.addEntryText("dateNaissance", (s: String) => "\'" + s + "\'", "Date de naissance", (s: String) => s)
            ep.addEntryText("assurance", (s: String) =>  "\'" + s + "\'", "Fournisseur d'assurance", (s: String) => s)
            ep.addEntryText("email", (s: String) =>  "\'" + s + "\'", "Adresse e-mail", (s: String) => s)
            ep.addEntryText("numeroTel", (s: String) =>  "\'" + s + "\'", "Numéro de téléphone", (s: String) => s)

            rs = Main.queries.queryDatabase("SELECT numero, rue, ville, province, adresse_id FROM Dentist.Adresses")
            val numeros = Main.queries.extractColumnStr(rs, "numero"); rs.beforeFirst()
            val rues = Main.queries.extractColumnStr(rs, "rue"); rs.beforeFirst()
            val villes = Main.queries.extractColumnStr(rs, "ville"); rs.beforeFirst()
            val provinces = Main.queries.extractColumnStr(rs, "province"); rs.beforeFirst()
            val ids = Main.queries.extractColumnStr(rs, "adresse_id")
            rs.close()

            val chs = new Array[String](numeros.length)
            val chVs = new Array[String](numeros.length)
            Range(0, numeros.length).foreach((i: Int) => {
                chs(i) = numeros(i) + " " + rues(i) + ", " + villes(i) + ", " + provinces(i)
                chVs(i) = ids(i)
            })

            ep.addEntrySelect("adresse_id", (s: String) => s, "Adresse", chs, chVs)

            dPanel.remove(content)
            content = ep
            dPanel.add(content)
            dPanel.updateUI()
        }
        }
    })
    val bookBtn = new JButton("Fixer rendez-vous")
    bookBtn.addActionListener(new ActionListener() {
        def actionPerformed(x: ActionEvent): Unit = {
            if (patientList.getSelectedIndex() != -1) {
            
            val pid = patient_ids(patientList.getSelectedIndex())

            var rs = Main.queries.queryDatabase("SELECT MAX(rdv_id) FROM Dentist.Rendezvous")
            rs.next()
            var rdvid = rs.getInt(1) + 1
            Main.queries.updateDatabase(List("INSERT INTO Dentist.Rendezvous (patient_id, statut) VALUES(" + pid + " , \'Pending\');"))
            
            // create entry panel
            val ep = new EditPanel("rdv_id", rdvid.toString, "Dentist.Rendezvous")
            ep.addEntryText("daterdv", (s: String) => "\'" + s + "\'", "Date", (s: String) => s)
            ep.addEntryText("heuredebut", (s: String) =>  s , "Débute à", (s: String) => s)
            ep.addEntryText("heurefin", (s: String) => s, "Finit à", (s: String) => s)
            ep.addEntryText("typerdv", (s: String) =>  "\'" + s + "\'", "Type", (s: String) => s)
            ep.addEntryText("chambreAttribue", (s: String) => s, "Chambre attribuée", (s: String) => s)

            rs = Main.queries.queryDatabase(
            "SELECT prenom, nom, username FROM (Dentist.Persons P NATURAL JOIN Dentist.Users U)" + 
            " WHERE EXISTS (SELECT * FROM Dentist.Employees E WHERE (U.username = E.username AND E.emploi_role = \'Dentist\'))"
            )
            val nom = Main.queries.extractColumnStr(rs, "nom"); rs.beforeFirst()
            val prenom = Main.queries.extractColumnStr(rs, "prenom"); rs.beforeFirst()
            var ids = Main.queries.extractColumnStr(rs, "username")
            rs.close()

            ids = ids.map((user: String) => {
                rs = Main.queries.queryDatabase("SELECT employee_id FROM Dentist.Employees WHERE (username = \'" + user + "\');")
                rs.next()
                rs.getString(1)
            })

            rs.close()

            val chs = new Array[String](nom.length)
            val chVs = new Array[String](nom.length)
            Range(0, nom.length).foreach((i: Int) => {
                chs(i) = prenom(i) + " " + nom(i)
                chVs(i) = ids(i)
            })

            ep.addEntrySelect("employee_id", (s: String) => s, "Dentiste", chs, chVs)

            dPanel.remove(content)
            content = ep
            dPanel.add(content)
            dPanel.updateUI()
        }
        }
    })
    btnPanel.setLayout(new GridLayout(1,3))
    btnPanel.add(viewBtn); btnPanel.add(editBtn); btnPanel.add(bookBtn)

    // dpanel contents
    var content = new JPanel()

    this.add(btnPanel, BorderLayout.SOUTH)

}

class PatientMode(patientID : String) extends JPanel {
    val res  = Main.queries.queryDatabase(
    "select rdv_id, daterdv from dentist.Rendezvous where (patient_ID = \'" + patientID + "\') order by daterdv")
    val dates = Main.queries.extractColumnStr(res, "daterdv")
    res.beforeFirst()
    val rdv_ids = Main.queries.extractColumnStr(res, "rdv_id")
    res.close()

    this.setLayout(new BorderLayout())

    val rdvList = new JList(dates)
    val scroll = new JScrollPane(rdvList)
    scroll.setPreferredSize(new Dimension(150,600))
    this.add(scroll, BorderLayout.WEST)
    this.setPreferredSize(new Dimension(950,700))

     // displaypanel

    val dPanel = new JPanel()
    dPanel.setPreferredSize(new Dimension(800, 600))
    this.add(dPanel, BorderLayout.EAST)

    // create command panel
    //val btnPanel = new JPanel()
    val viewBtn = new JButton("Voir information")
    viewBtn.addActionListener(new ActionListener() {
        def actionPerformed(x: ActionEvent): Unit = {
            if (rdvList.getSelectedIndex() != -1) {
            val dt = new DisplayTuple("rdv_id", rdv_ids(rdvList.getSelectedIndex()), "Dentist.Rendezvous")
            dt.addEntry("daterdv", (s:String) => s, "Date")
            dt.addEntry("heureDebut", (s:String) => s + ":00", "Débute à")
            dt.addEntry("heureFin", (s:String) => s + ":00", "Finit à")
            dt.addEntry("typerdv", (s:String) => s, "Type")
            dt.addEntry("statut", (s:String) => s, "Statut")
            dt.addEntry("chambreAttribue", (s:String) => s, "Chambre attribuée")
            dPanel.remove(content)
            content = dt
            dPanel.add(content)
            dPanel.updateUI()
        }
        }
    })

    // dpanel contents
    var content = new JPanel()

    this.add(viewBtn, BorderLayout.SOUTH)
}

class DentistMode(employeeID : String) extends JPanel {
    // create patient list
    val res  = Main.queries.queryDatabase("select * from (dentist.persons natural join dentist.patients)" +
    " where exists (select * from dentist.estPatientde E where (E.patient_id = patient_id AND E.employee_id = " + employeeID + ")) order by nom;")
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

    val viewBtn = new JButton("Voir information")
    viewBtn.addActionListener(new ActionListener() {
        def actionPerformed(x: ActionEvent): Unit = {
            if (patientList.getSelectedIndex() != -1) {

            val pid = patient_ids(patientList.getSelectedIndex())
            val rs = Main.queries.queryDatabase("SELECT procedure_id FROM Dentist.Procedurerdv " +
            "WHERE patient_id = " + pid + ";")
            rs.next()
            val proID = rs.getString(1); rs.close()

            val dt = new DisplayTuple("procedure_id", proID, "Dentist.Procedurerdv")
            dt.addEntry("procedureCode", (s:String) => s, "Procedure Code")
            dt.addEntry("description_", (s:String) => s , "Description")
            dt.addEntry("daterdv", (s:String) => s, "Date")
            dt.addEntry("quantiteProcedure", (s:String) => s, "Quantité")
            dt.addEntry("dentImplique", (s:String) => s, "Dents impliquées")
            dPanel.remove(content)
            content = dt
            dPanel.add(content)
            dPanel.updateUI()
        }
        }
    })


     // displaypanel

    val dPanel = new JPanel()
    dPanel.setPreferredSize(new Dimension(800, 600))
    this.add(dPanel, BorderLayout.EAST)

    // dpanel contents
    var content = new JPanel()

    this.add(viewBtn, BorderLayout.SOUTH)
}