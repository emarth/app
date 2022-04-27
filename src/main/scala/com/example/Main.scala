package com.example

import javax.swing.{JPanel, JFrame, WindowConstants, JLabel}
import java.awt.CardLayout

object Main extends App {

    val cl = new CardLayout()
    val mainPanel = new JPanel(cl)
    val nextPanel = new JPanel()
    nextPanel.add(new JLabel("Success!"))
    cl.addLayoutComponent(nextPanel, "next")

    val login = new LoginPanel(() => {cl.show(mainPanel, "next")})
    mainPanel.add(login)
    mainPanel.add(nextPanel)
    
    val frame = new JFrame()
    frame.add(mainPanel)
    frame.setVisible(true)
    frame.setSize(500,500)
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
}