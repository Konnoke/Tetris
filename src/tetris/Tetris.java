/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.ArrayList;
/**
 *
 * @author Zarathustra aka Kevin aka Konnoke NOTE TO DANEEL: NOT NEEDED BUT GOOD
 * REFERENCE TO KEEP FOR NOW TO INCORPORATE INTO TETRIS CLIENT
 */
public class Tetris {

    JLabel score;
    private int scoreInt;
    private int port;
    private String host;
    
    private JPanel tetrisScoreboard;
    private JPanel chatroom;
    private JFrame tetrisWorld;
    
    JTable ScoreBoard;
    public Tetris() throws IOException {
        tetrisWorld =  new JFrame("Tetris");
        tetrisWorld.setVisible(true);
        tetrisWorld.setSize(600,800);
        tetrisWorld.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        displayTetris();
        displayChatRoom();
        displayScoreBoard();
        
    }
    
    private void displayTetris(){
        Board board = new Board(this);
        board.setBorder(BorderFactory.createLineBorder(Color.black, 4));
        tetrisWorld.add(board);
        board.setSize(300, 600);
        board.start();
    }
    private void displayChatRoom() throws IOException{
        chatroom =  new JPanel();
        chatroom.setSize(300,800);
        chatroom.setBorder(BorderFactory.createLineBorder(Color.black, 4));
        tetrisWorld.add(chatroom);
//        TetrisClient client =  new TetrisClient();
//        client.setBorder(BorderFactory.createLineBorder(Color.black, 4));
//        tetrisWorld.add(client);
//        client.setSize(300, 600);
//        client.run();
    }
    private void displayScoreBoard() {
        ScoreBoard =  new JTable(10, 2);
        LeaderboardServer server = new LeaderboardServer();
       
        //how to populate gui
        for(int i = 0; i<server.leaderboard.size();i++ ){
           
        }
    }
    public static void main(String[] args) throws IOException {
         Tetris game = new Tetris();
        

    }



}
