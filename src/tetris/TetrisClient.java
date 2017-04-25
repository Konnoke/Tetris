/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TetrisClient extends JPanel {

  BufferedReader in;
  PrintWriter out;
  JFrame frame = new JFrame("Chat-Room");
  JTextField textBox = new JTextField(30);
  JTextArea messageArea = new JTextArea(8, 30);
  Player user = new Player("", 0);

  public TetrisClient() {

    textBox.setEditable(false);
    messageArea.setEditable(false);
    frame.getContentPane().add(textBox, "South");
    frame.getContentPane().add(new JScrollPane(messageArea), "Center");
    
    //Board board = new Board(user);
    
    frame.pack();

    textBox.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        out.println(textBox.getText());
        textBox.setText("");
      }
    });
    
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    
  }

  private String getServerAddress() {
    return "127.0.0.1";
  }

  public String getName() {
    return JOptionPane.showInputDialog(
        frame,
        "Choose a name:",
        "Register",
        JOptionPane.PLAIN_MESSAGE);
  }

  void run() throws IOException {

    String serverAddress = getServerAddress();
    Socket socket = new Socket(serverAddress, 9001);
    in = new BufferedReader(new InputStreamReader(
        socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream(), true);
    while (true) {
      String line = in.readLine();
      if (line.startsWith("SUBMITNAME")) {
        out.println(getName());
      } else if (line.startsWith("NAMEACCEPTED")) {
        textBox.setEditable(true);
      } else if (line.startsWith("MESSAGE")) {
        messageArea.append(line.substring(8) + "\n");
      }
    }
  }

}
