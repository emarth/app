package com.example

import javax.swing.{JPanel, JFrame, WindowConstants, JLabel, JComboBox}
import java.awt.CardLayout
import java.awt.event.ActionListener
import java.awt.event.ActionEvent

object Main extends App {

    var username = ""
    val cl = new CardLayout()
    val modeCL = new CardLayout()
    val mainPanel = new JPanel(cl)
    val nextPanel = new JPanel()
    val modePanel = new JPanel(modeCL)
    var modeEnvs = List[JPanel]()
    var modeSelect = new JComboBox[String]()
    cl.addLayoutComponent(nextPanel, "next")

    val login = new LoginPanel((user: String) =>
    {
        cl.show(mainPanel, "next")
        username = user
        val res = Queries.queryDatabase("select prenom, nom from (users natural join persons) where (username = \'" + user + "\' )")
        res.next()
        val name = res.getString("prenom") + " " + res.getString("nom")
        nextPanel.add(new JLabel("Bienvenue " + name + "! Quelle mode voulez-vous utiliser?     "))

        val modes = ModeUtils.getModes(username)
        val modesArray : Array[String] = Array.ofDim[String](modes.size)
        modes.keys.copyToArray(modesArray)
        modeSelect = new JComboBox[String](modesArray)
        modeSelect.addActionListener(new ModeSelectAL)

        modesArray.foreach((mode: String) => {
            val mp = new ModePanel(mode, username)
            modeEnvs = modeEnvs :+ mp
            modeCL.addLayoutComponent(mp, mode)
        })

        modeEnvs.foreach(modePanel.add)
        nextPanel.add(modeSelect)
        nextPanel.add(modePanel)
    })

    mainPanel.add(login)
    mainPanel.add(nextPanel)
    
    // dropdown action handler

    class ModeSelectAL extends ActionListener {
        def actionPerformed(x: ActionEvent): Unit = {
            val selected = modeSelect.getItemAt(modeSelect.getSelectedIndex())
            modeCL.show(modePanel, selected)
        }
    }

    val frame = new JFrame()
    frame.add(mainPanel)
    frame.setVisible(true)
    frame.setSize(1000,800)
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
}