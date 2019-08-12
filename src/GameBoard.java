// Brandon Evans - Saturday April 3, 2019
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class GameBoard {

    // Used for moves
    public Object[] move = new Object[2];
    Button[][] gbArr = new Button[8][8];
    int row1,col1,row2,col2;
    LogicEngine play = new LogicEngine();
    String plyr = "W";
    Label info;
    Label deadP1;
    Label deadP2;
    ArrayList<String> dP1 = new ArrayList<String>();
    ArrayList<String> dP2 = new ArrayList<String>();

    public Pane createBoard() {
        Pane gameBoard = new Pane();
        Pane mainPane = new Pane();
        gameBoard.setMinHeight(800);
        gameBoard.setMinWidth(800);
        GridPane gridBoard = new GridPane();
        gridBoard.setMinSize(800,800);

        // 2d Array for game board
        for (int i = 0;i < 8;i++) {
            for (int j = 0;j < 8;j++) {
                Button btn = new Button("X");
                btn.setMinSize(100,100);
                boardHandler handle = new boardHandler();
                btn.setOnAction(handle);
                gbArr[i][j] = btn;
            }
        }
        // Applying buttons in the array to gridPane for display
        for (int i = 0;i < 8;i++) {
            for (int j = 0;j < 8;j++) {
                gridBoard.add(gbArr[i][j],i,j);
            }
        }

        VBox screen = new VBox();
        HBox topOfScreen = new HBox();
        HBox bottomOfScreen = new HBox();

        deadP1 = new Label("Dead Players White");
        deadP1.setMinSize(400,50);
        deadP1.setAlignment(Pos.CENTER);
        deadP2 = new Label("Dead Players Black");
        deadP2.setMinSize(400,50);
        deadP2.setAlignment(Pos.CENTER);
        info = new Label("The Current Player is: " + plyr);
        info.setMinSize(700,50);
        info.setAlignment(Pos.CENTER);
        topOfScreen.getChildren().addAll(deadP1,deadP2);
        bottomOfScreen.getChildren().addAll(info);

        screen.getChildren().addAll(topOfScreen,gridBoard,bottomOfScreen);
        gameBoard.getChildren().add(screen);
        mainPane.getChildren().add(gameBoard);

        // Creating the starting board
        String piece;
        for (int row = 0; row < 8;row++) {
            for (int  col = 0;col < 8;col++) {
                piece = play.startGame(row,col);
                gbArr[row][col].setText(piece);
                Image img = play.piecePic(piece);
                gbArr[row][col].setGraphic(new ImageView(img));
//                Image img = new Image("./images/QueenWhite.png");
//                gbArr[row][col].setGraphic(new ImageView(img));
            }
        }



        return mainPane;
    }

    private class boardHandler implements EventHandler<ActionEvent> {
        Boolean moved = false;
        public void handle(ActionEvent event) {
            if (event != null) {
                if (move[0] == null && move[1] == null) {
                    for (int row = 0;row < 8;row++) {
                        for (int col = 0;col < 8;col++) {
                            if (event.getSource().equals(gbArr[row][col])) {
                                row1 = row;
                                col1 = col;
                                move[0] = gbArr[row][col];
                            }
                        }
                    }
                } else if(move[1] == null) {
                    for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                            if (event.getSource().equals(gbArr[row][col])) {
                                row2 = row;
                                col2 = col;
                                if (play.isMoveValid(gbArr, (Button) move[0], plyr, row1, col1, row2, col2)) {
                                    // Checks for kill and if trying to move to friendly piece
                                    if (play.isKill(gbArr,row2,col2,plyr)) {
                                        if (plyr.equals("B")) {
                                            dP1.add(gbArr[row2][col2].getText());
                                        } else {
                                            dP2.add(gbArr[row2][col2].getText());
                                        }
                                        gbArr[row2][col2].setText(gbArr[row1][col1].getText());
                                        Image img1 = play.piecePic(gbArr[row1][col1].getText());
                                        gbArr[row2][col2].setGraphic(new ImageView(img1));
                                        gbArr[row1][col1].setGraphic(null);
                                        gbArr[row1][col1].setText("");
                                        moved = true;
                                        String str = "";
                                        for (int i = 0;i < dP1.size();i++) {
                                            str += dP1.get(i) +" ";
                                        }
                                        deadP1.setText(str);
                                        str = "";
                                        for (int i = 0;i < dP2.size();i++) {
                                            str += dP2.get(i) + " ";
                                        }
                                        deadP2.setText(str);
                                        str = "";
                                    } else {
                                        if (gbArr[row2][col2].getText().equals("")) {
                                            gbArr[row2][col2].setText(gbArr[row1][col1].getText());
                                            Image img1 = play.piecePic(gbArr[row1][col1].getText());
                                            gbArr[row2][col2].setGraphic(new ImageView(img1));
                                            gbArr[row1][col1].setGraphic(null);
                                            gbArr[row1][col1].setText("");
                                            moved = true;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (play.checkWin(gbArr, plyr)) {
                        System.out.println("Program has been completed");
                        Text txt = new Text();
                        txt.setText(plyr + " has won the game! Game Over.");
                        info.setText(txt.getText());

                    } else {
                        if (moved == true) {
                            if (plyr.equals("W")) {
                                plyr = "B";
                            } else {
                                plyr = "W";
                            }

                            info.setText("The current player is: " + plyr);
                        }



                        move[0] = null;
                        move[1] = null;
                        moved = false;
                    }


                }
            }
        }
    }


}
