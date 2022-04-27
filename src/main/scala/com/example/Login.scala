package com.example

import javax.swing.{JFrame, JPanel, JButton, JTextField}
import java.awt.event.ActionListener
import java.sql.DriverManager
import java.sql.Connection
import java.awt.event.ActionEvent
import java.sql.SQLException
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

class LoginPanel(succesFunc : () => Unit) extends JPanel {

	// variables and values
  val button = new JButton("Login")
  val username = new JTextField("Enter username")
  val password = new JTextField("Enter password")
  
	// check database func

	def authenaticate(username: String, password: String): Boolean = {
		val url = "jdbc:postgresql://localhost:5432/postgres"
		val user = "postgres"
		val pass = "password"

		var conn: Connection = null
		try {
			conn = DriverManager.getConnection(url, user, pass)
		} catch {
			case e: SQLException => {e.printStackTrace(); false}
		}

		val stm = conn.createStatement()
		val query = "select true where exists (select * from users where username = \'" + username +
		"\' and pass_word = \'" + password + "\')"
		val result = stm.executeQuery(query)
		// checks if u + p combo exists in users
		result.next()
	}

	// button click

  class LoginAL extends ActionListener {
      def actionPerformed(x: ActionEvent): Unit = {
				if(authenaticate(username.getText(), password.getText())) {succesFunc()}
			}
  }
  button.addActionListener(new LoginAL())

	// panel

	this.add(username)
	this.add(password)
	this.add(button)

}

