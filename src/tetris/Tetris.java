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

/**
 *
 * @author Zarathustra aka Kevin aka Konnoke
 */
public class Tetris extends JFrame {

  JLabel score;
  private int port;
  private String host;
  
  
  public Tetris(String host, int port) throws IOException {
    this.host = host;
    this.port = port;
    
    score = new JLabel(" 0");
    Board board = new Board(this);
    add(board);
    board.start();

    setSize(300, 600);
    setTitle("Tetris");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public JLabel getScore() {
    return score;
  }

  public static void main(String[] args) throws IOException {
    Tetris game = new Tetris("localhost", 5003);
    //Thread game = new Tetris("localhost", 5003);
    game.setLocationRelativeTo(null);
    game.setVisible(true);
  }

}
