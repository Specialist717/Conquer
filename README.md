# Conquer
Conquer is a strategic card game where players compete to control the board by placing cards from their hand. The game is won by creating a path of 5 cards in a row horizontally, vertically, or diagonally. The game is implemented in Java and uses Swing for the graphical user interface.

https://github.com/Specialist717/Conquer/assets/117452469/922dbd9d-79c0-4a87-8c26-1f2756de440e

<p align="center">
  <img alt="Starting message" src="./showcase/starting message.png" width="40%">
&nbsp; &nbsp; &nbsp; &nbsp;
  <img alt="End message" src="./showcase/ending message.png" width=40%>
</p>

## Technologies Used
- Java: The game logic and GUI are implemented in Java.
- Swing: Used for creating the graphical user interface.

## Project Structure
The project is divided into three main packages:

- `model`: Contains the classes for the game logic, such as `Card` and `Deck`.
- [`view`](command:_github.copilot.openSymbolInFile?%5B%22Projekt%2Fsrc%2Fview%2FGameWindow.java%22%2C%22view%22%5D "Projekt/src/view/GameWindow.java"): Contains the classes for the GUI, such as [`GameWindow`](command:_github.copilot.openSymbolInFile?%5B%22Projekt%2Fsrc%2Fview%2FGameWindow.java%22%2C%22GameWindow%22%5D "Projekt/src/view/GameWindow.java") and [`StartWindow`](command:_github.copilot.openSymbolInFile?%5B%22Projekt%2Fsrc%2Fview%2FStartWindow.java%22%2C%22StartWindow%22%5D "Projekt/src/view/StartWindow.java").
- `network`: Contains the classes for the network communication, such as `Client` and `Server`.

The game assets, such as images, are located in the `pictures` directory.

## How to Run
1. Clone the repository to your local machine.
2. Open the project in your Java IDE of choice.
3. Run the `Main` class located in the `model` package.

## How to Play
#### Firstly there are two options to play Conquer:

**Local Play**: Run two instances of the application on the same computer. One instance will act as the server and the other as the client. This is done automatically when you run the [`Main`](command:_github.copilot.openSymbolInFile?%5B%22Projekt%2Fsrc%2Fmodel%2FMain.java%22%2C%22Main%22%5D "Projekt/src/model/Main.java") class from [`Projekt/src/model/Main.java`](command:_github.copilot.openRelativePath?%5B%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Flenovo%2FDesktop%2FProgramowanie%20-%20projekt%2FProjekt%2Fsrc%2Fmodel%2FMain.java%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%5D "c:\Users\lenovo\Desktop\Programowanie - projekt\Projekt\src\model\Main.java"). If a server is not active, the application will start as a server. If a server is active, the application will start as a client.

**Network Play**: Play on two separate machines through the web. First, run the server on one machine. The server's IP will be printed to the console. Then, on the other machine, update the [`SERVER_IP`](command:_github.copilot.openSymbolInFile?%5B%22Projekt%2Fsrc%2Fnetwork%2FClient.java%22%2C%22SERVER_IP%22%5D "Projekt/src/network/Client.java") constant in the [`Client`](command:_github.copilot.openSymbolInFile?%5B%22Projekt%2Fsrc%2Fnetwork%2FClient.java%22%2C%22Client%22%5D "Projekt/src/network/Client.java") class to the server's IP.
```java
public class Client {
    private static final String SERVER_IP = "192.168.0.114"; // Update this to your server's IP
    /*...*/
}
```
Remember to ensure that the machines are connected to the same network and the server's port (4444 by default) is open and accessible.

#### And finally 
1. Place cards on the board from your hand of 18 cards, ranging from power 1-18.
2. Cards can be played on empty spaces or over lower-powered enemy cards.
3. Secure victory with a path of 5 cards in a row horizontally, vertically, or diagonally.

## Plans for the future
-Make the application easier to navigate.

-Add startup screen with falling numbers as background.
 
https://github.com/Specialist717/Conquer/assets/117452469/6ca0e395-3a24-499a-8dad-18e49d30f3bf
  
#### Enjoy! 
