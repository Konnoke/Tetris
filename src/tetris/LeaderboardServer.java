package tetris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class LeaderboardServer {

  private static final int PORT = 9001;
  private static HashSet<String> names = new HashSet<String>();
  private static ArrayList<Player> leaderboard = new ArrayList<Player>();

  static Player one = new Player("Kevin", 9100);
  static Player two = new Player("Kemar", 9001);
  static Player three = new Player("Kalvin", 6900);
  static Player four = new Player("Ana", 4000);
  static Player five = new Player("Bran", 2000);
  static Player six = new Player("Candy", 1800);
  static Player seven = new Player("Dana", 1300);
  static Player eight = new Player("Ethan", 1000);
  static Player nine = new Player("Freddy", 500);
  static Player ten = new Player("George", 100);

  //holds all the messages
  private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

  public static void main(String[] args) throws Exception {
    leaderboard.add(0, one);
    leaderboard.add(1, two);
    leaderboard.add(2, three);
    leaderboard.add(3, four);
    leaderboard.add(4, five);
    leaderboard.add(5, six);
    leaderboard.add(6, seven);
    leaderboard.add(7, eight);
    leaderboard.add(8, nine);
    leaderboard.add(9, ten);

    System.out.println("Server has started");
    ServerSocket listener = new ServerSocket(PORT);
    try {
      while (true) {
        new ChatThread(listener.accept()).start();
      }
    } finally {
      listener.close();
    }
  }
  
  public static void updateLeaderboard(Player newChallenger){
    for(int i = 0; i < 10; i++){
      if(newChallenger.score > leaderboard.get(i).score){
        leaderboard.add(i, newChallenger);
        leaderboard.remove(10);
      }
    }
  }

  private static class ChatThread extends Thread {

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
  }
  
  
  
}
