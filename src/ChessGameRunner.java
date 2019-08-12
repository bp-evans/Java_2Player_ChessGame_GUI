// Brandon Evans - Saturday April 6, 2019
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.*;
import javafx.scene.layout.Pane;

public class ChessGameRunner extends Application {
    public void Run() {
        System.out.println("Brandon Evans\t Chess Game: v0.1");
        System.out.println("------------------------------------------");
        String input = "";
        System.out.println("Please enter one of the commands below");
        System.out.println("\'s\'                 Start The Game");
        System.out.println("\'q\'                 Quit The Program");
        Scanner inputS = new Scanner(System.in);
        input = inputS.nextLine();
        if (input.toLowerCase().equals("s")) {
            System.out.println("Starting Game.....");
            Application.launch();
        } else if (input.toLowerCase().equals("q")) {
            System.out.println("Goodbye.");
        } else {
            System.out.println("Invalid Input. Goodbye.");
        }
    }
    // Starts the actual chess game
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameBoard board = new GameBoard();
        Pane p = board.createBoard();
        Scene scene1 =  new Scene(p);
        primaryStage.setTitle("Chess Game v0.1");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}
