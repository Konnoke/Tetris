/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Zarathustra
 */
public class LeaderboardServer {
  private ServerSocket serverSocket;

  public LeaderboardServer(int port) throws IOException {
    serverSocket = new ServerSocket(port);
  }

  public void run() {
    try {
      Socket client = serverSocket.accept();

      DataInputStream in = new DataInputStream(client.getInputStream());
      BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
      DataOutputStream out = new DataOutputStream(client.getOutputStream());

      while (true) {
        
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
