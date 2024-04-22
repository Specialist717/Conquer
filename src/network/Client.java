package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import view.GameWindow;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 4444;
    private String update;
    private InputStreamReader in = null;
    private BufferedReader bf = null;
    private PrintWriter out = null;
    private Socket socket = null;
    private GameWindow gameWindow = null;

    public Client() throws IOException {
        try {
            this.socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to server, starting game...");

            this.gameWindow = new GameWindow(false);
            System.out.println("GUI Initialized");
            new Thread(() -> gameWindow.displayIntro()).start();

            this.in = new InputStreamReader(socket.getInputStream());
            this.bf = new BufferedReader(in);
            this.out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                boolean doesServerStarts = Boolean.parseBoolean(bf.readLine());
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
            throw new IOException();
        }
    }

    public void endConnection(){
        try{
            this.out.close();
            this.in.close();
            this.bf.close();
            this.socket.close();
        } catch (IOException e) {
            System.out.println("Error when closing resources");
            e.printStackTrace();
        }
    }

    public void clientStarts() throws Exception{
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

    public void serverStarts() throws Exception {
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

    public static void main(String[] args) throws IOException{
    }
}