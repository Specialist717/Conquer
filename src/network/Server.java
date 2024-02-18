package network;

import view.GameWindow;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    private static final int PORT = 4444;
    private String update;
    private Random random = new Random(); 
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private InputStreamReader in = null;
    private PrintWriter out = null;
    private BufferedReader bf = null;
    private GameWindow gameWindow = null;
    

    public Server() {
        try {
            this.serverSocket = new ServerSocket(PORT);
            System.out.println("Server started, waiting for client...");
            printServerIP();
            this.clientSocket = serverSocket.accept();
            System.out.println("Client connected, starting game...");

            this.gameWindow = new GameWindow(true);
            System.out.println("GUI Initialized");
            new Thread(() -> gameWindow.displayIntro()).start();

            this.in = new InputStreamReader(clientSocket.getInputStream());
            this.bf = new BufferedReader(in);
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);           

            while (true) {
                boolean doesServerStarts = random.nextBoolean();
                out.println(doesServerStarts);
                if (doesServerStarts) {
                    serverStarts();
                } else {
                    clientStarts();
                }
                try {
                    if (gameWindow.isGameFinished()) {
                        endConnection();
                        break;
                    }
                } catch (Exception e) {
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public void endConnection(){
        try{
            this.out.close();
            this.in.close();
            this.bf.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error when closing resources");
            e.printStackTrace();
        }
    }

    public void serverStarts() throws Exception{
        while (true) {
            gameWindow.updateTurnLabel(true);
            gameWindow.setDropEnabled(true);
            while(gameWindow.isDropEnabled()) {
                Thread.sleep(100);
            }  
            if (gameWindow.checkWinCondition()) {
                out.println("You lost!" + gameWindow.getLastPlacedCardInfo());
                gameWindow.displayMessage("You won!");
                break;
            }   
            out.println(gameWindow.getLastPlacedCardInfo());
            if (gameWindow.checkIfDraw()) {
                gameWindow.drawCard();
                String isdraw = bf.readLine();
                if (isdraw.startsWith("Draw!")){
                    String[] parts = isdraw.split("!");
                    gameWindow.updateBoard(parts[1]);
                    gameWindow.displayMessage("Draw!");
                } else if (isdraw.startsWith("You lost!")) {
                    String[] parts = isdraw.split("!");
                    gameWindow.updateBoard(parts[1]);
                    gameWindow.displayMessage("You lost!");
                }
                break;
            }
            gameWindow.drawCard();

            gameWindow.updateTurnLabel(false);
            update = bf.readLine();
            if (update.startsWith("You lost!")) {
                String[] parts = update.split("!");
                gameWindow.updateBoard(parts[1]);
                gameWindow.displayMessage("You lost!");
                break;
            }
            gameWindow.updateBoard(update);
        }
    }

    public void clientStarts() throws Exception {
        while (true) {
            new Thread(() -> gameWindow.updateTurnLabel(false)).start();
            update = bf.readLine();
            if (update.startsWith("You lost!")) {
                String[] parts = update.split("!");
                gameWindow.updateBoard(parts[1]);
                gameWindow.displayMessage("You lost!");
                break;
            }
            gameWindow.updateBoard(update);
        
            gameWindow.updateTurnLabel(true);
            gameWindow.setDropEnabled(true);
            while(gameWindow.isDropEnabled()) {
                Thread.sleep(100);
            }    
            if (gameWindow.checkWinCondition()) {
                out.println("You lost!" + gameWindow.getLastPlacedCardInfo());
                gameWindow.displayMessage("You won!");
                break;
            }    
            if (gameWindow.checkIfDraw()) {
                gameWindow.drawCard();
                out.println("Draw!" + gameWindow.getLastPlacedCardInfo());
                gameWindow.displayMessage("Draw!");
                break;
            }
            out.println(gameWindow.getLastPlacedCardInfo());
            gameWindow.drawCard();
        }
    }

    public void printServerIP() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("Server IP Address: " + inetAddress.getHostAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }
}