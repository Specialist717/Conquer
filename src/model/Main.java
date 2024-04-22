package model;

import network.Client;
import network.Server;

public class Main {
 
public static void main(String[] args) {

    try {
        new Client(); 
        System.out.println("Connected to server, starting as client...");
    } catch (Exception e) {
        System.out.println("No active server, starting as server...");
        new Server();
    }
    }
}