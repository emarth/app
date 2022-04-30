package com.example

import javax.swing.{JPanel, JLabel}

object ModeUtils {
    
    val getModes = (username : String) => {
        var res = Queries.queryDatabase(
        "select patient_id, prenom from (persons natural join patients)" +
        "where (username = \'" + username + "\');"
        )
        val patientModes = Queries.extractColumnStr(res, "prenom")
        res.beforeFirst()
        val patientIds = Queries.extractColumnInt(res, "patient_id")

        res = Queries.queryDatabase(
            "select employee_id, emploi_role from employees where (username = \'" +
            username + "\');"
        )

        val employeeModes = Queries.extractColumnStr(res, "emploi_role")
        res.beforeFirst()
        val employeeIds = Queries.extractColumnInt(res, "employee_id")
        
        var modes : Map[String, Int] = Map()

        Range(0, patientModes.length).foreach((i: Int) => {modes = modes + ("Patient: " + patientModes(i) -> patientIds(i))})
        Range(0, employeeModes.length).foreach((i: Int) => {modes = modes + ("[Employé(e)] Rôle: " + employeeModes(i) -> employeeIds(i))})
        modes 
    }

}

class ModePanel(mode : String, username : String) extends JPanel {
    this.add(new JLabel("Vous êtes en mode " + mode + "."))
}