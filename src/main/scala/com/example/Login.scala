package com.example

import javax.swing.{JFrame, JPanel, JButton, JTextField, JLabel}
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.FlowLayout
import javax.swing.BoxLayout
import java.awt.Dimension

class LoginPanel(succesFunc : (String) => Unit) extends JPanel {

	// variables and values
  val button = new JButton("Login")

	val userLabel = new JLabel("Username: ")
  val userPanel = new JPanel()
	val usertf = new JTextField()
	usertf.setPreferredSize(new Dimension(100, 20))

  val passLabel = new JLabel("Password: ")
  val passPanel = new JPanel()
	val passtf = new JTextField()
	passtf.setPreferredSize(new Dimension(100, 20))

	userPanel.add(userLabel)
	userPanel.add(usertf)
	passPanel.add(passLabel)
	passPanel.add(passtf)
  
	// check database func

	def authenaticate(username: String, password: String): Boolean = {
		val query = "select true where exists (select * from dentist.Users where username = \'" + username +
		"\' and pass_word = \'" + password + "\')"
		val result = Main.queries.queryDatabase(query)
		// checks if u + p combo exists in users
		result.next()
	}

	// button click

  class LoginAL extends ActionListener {
      def actionPerformed(x: ActionEvent): Unit = {
				if(authenaticate(usertf.getText(), passtf.getText())) {succesFunc(usertf.getText())}
			}
  }
  button.addActionListener(new LoginAL())

	// main panel

	this.add(userPanel)
	this.add(passPanel)
	this.add(button)

}

