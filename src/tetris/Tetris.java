/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static tetris.Tetris.board;

/**
 *
 * @author Zarathustra aka Kevin aka Konnoke
 * NOTE TO DANEEL:  NOT NEEDED BUT GOOD REFERENCE TO KEEP FOR NOW TO INCORPORATE INTO TETRIS CLIENT
 */
public class Tetris extends JFrame {

  JLabel score;
  private int scoreInt;
  private int port;
  private String host;
  private static final int PORT = 9001;
  private static HashSet<String> names = new HashSet<String>();
  private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
  static Board board;
  

  
  
  public Tetris() throws IOException {
 
    
    
    score = new JLabel(" 0");
     add(score, BorderLayout.SOUTH);
     board = new Board(this);
    add(board);
    board.start();
    
    setSize(700, 1200);
    setTitle("Tetris");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  

  public JLabel getScore() {
    return score;
  }

  public static void main(String[] args) throws IOException {
    Tetris game = new Tetris();
    //Thread game = new Tetris("localhost", 5003);
    game.setLocationRelativeTo(null);
    game.setVisible(true);
        System.out.println("Server has started");
    ServerSocket listener = new ServerSocket(PORT);
    try {
      while (true) {
        new Tetris.ChatThread(listener.accept()).start();
      }
    } finally {
      listener.close();
    }
  }
  
public static class ChatThread extends Thread {

    private String name;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int firstMessage = 0;

    public ChatThread(Socket socket) {
      this.socket = socket;
    }

    public void run() {
      try {

        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {
          out.println("SUBMITNAME");
          name = in.readLine();
          synchronized (names) {
            if (!names.contains(name)) {
              names.add(name);
              break;
            }
          }
        }

        out.println("NAMEACCEPTED");
        writers.add(out);
        System.out.println(name + " has entered the chat.");
        while (true) {
          String input = in.readLine();
          if (input == null) {

            return;
          }
          for (PrintWriter writer : writers) {
            writer.println("MESSAGE " + name + ": " + input);
            board.adpt.keyPressed(input);
            System.out.println(name + ": " + input);
          }
        }
      } catch (IOException e) {
        System.out.println(name + " has left the chat.");
      } finally {
        if (name != null) {
          names.remove(name);
        }
        if (out != null) {
          writers.remove(out);
        }
        try {
          socket.close();
        } catch (IOException e) {
        }
      }
    }
  }}
