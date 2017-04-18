package tetris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;


public class ChatServer {

    private static final int PORT = 9001;
    private static HashSet<String> names = new HashSet<String>();

    //holds all the messages
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

    public static void main(String[] args) throws Exception {
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
