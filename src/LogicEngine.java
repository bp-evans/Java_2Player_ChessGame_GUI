import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class LogicEngine {
    // Sets the pieces for the starting game board
    public String startGame(int row, int col) {
        if (col == 1 && row >= 0 && row <= 7) {
            return "PawnB";
        } else if (col == 6 && row >= 0 && row <= 7) {
            return "PawnW";
        } else if ( (col == 0 && (row == 0 || row == 7)) || (col == 7 && (row == 0 || row == 7)) ) {
            if (col == 0) return "RookB";
            else return "RookW";
        } else if ( (col == 0 && (row == 1 || row == 6)) || (col == 7 && (row == 1 || row == 6)) ) {
            if (col == 0) return "KnightB";
            else return "KnightW";
        } else if ( (col == 0 && (row == 2 || row == 5)) || (col == 7 && (row == 2 || row == 5)) ) {
            if (col == 0) return "BishopB";
            else return "BishopW";
        } else if ((col == 0 || col == 7) && row == 3) {
            if (col == 0) return "KingB";
            else return "KingW";
        } else if ((col == 0 || col == 7) && row == 4) {
            if (col == 0) return "QueenB";
            else return "QueenW";
        } else {
            return "";
        }
    }

    public Image piecePic(String piece) {
        Image img = null;
        switch (piece) {
            case "PawnW":
                img = new Image("./images/PawnWhite.png");
                break;
            case "PawnB":
                img = new Image("./images/PawnBlack.png");
                break;
            case "RookW":
                img = new Image("./images/RookWhite.png");
                break;
            case "RookB":
                img = new Image("./images/RookBlack.png");
                break;
            case "BishopW":
                img = new Image("./images/BishopW.png");
                break;
            case "BishopB":
                img = new Image("./images/BishopBlack.png");
                break;
            case "KnightW":
                img = new Image("./images/KnightW.png");
                break;
            case "KnightB":
                img = new Image("./images/KnightBlack.png");
                break;
            case "KingW":
                img = new Image("./images/KingWhite.png");
                break;
            case "KingB":
                img = new Image("./images/KingBlack.png");
                break;
            case "QueenW":
                img = new Image("./images/QueenWhite.png");
                break;
            case "QueenB":
                img = new Image("./images/QueenBlack.png");
                break;
        }
        return img;
    }

    // Determines if the piece can make the desired move
    public boolean isMoveValid(Button[][] gbArr, Button txt, String player, int row1, int col1, int row2, int col2) {
        String p = player;
        if (!(txt.getText().equals("")))
            p = txt.getText().substring(0,txt.getText().length()-1);
        if (txt.getText().substring(txt.getText().length()-1,txt.getText().length()).equals(player) && (row1 != row2 || col1 != col2) ) {
            switch (p) {
                case "Pawn":
                    if (player.equals("W")) {
                        if ( (col2 == col1 - 1 && col2 >= 0)) {
                            if ( (row2 == row1 + 1 || row2 == row1 - 1) && (row2 <= 7 && row2 >= 0) ) {
                                if (isKill(gbArr,row2,col2,player)) {
                                    return true;
                                }
                            } else {
                                if (row2 == row1) {
                                    return true;
                                }
                            }
                        } else if (col2 == col1 + 1 && (col2 <= 7)) {
                            if ( (row2 == row1 + 1 || row2 == row1 -1) && (row2 <= 7 && row2 >= 0) ) {
                                if (isKill(gbArr,row2,col2,player)) {
                                    return true;
                                }
                            }
                        }
                        if (col1 == 6 && col2 == col1 - 2) {
                            return true;
                        }
                    } else {
                        if ( (col2 == col1 + 1 && col2 <= 7) ) {
                            if ( (row2 == row1 + 1 || row2 == row1 - 1) && (row2 <= 7 && row2 >= 0) ) {
                                if (isKill(gbArr,row2,col2,player)) {
                                    return true;
                                }
                            } else {
                                if (row2 == row1) {
                                    return true;
                                }
                            }
                        } else if (col2 == col1 - 1 && (col2 >= 0)) {
                            if ( (row2 == row1 + 1 || row2 == row1 -1) && (row2 <= 7 && row2 >= 0) ) {
                                if (isKill(gbArr,row2,col2,player)) {
                                    return true;
                                }
                            }
                        }
                        if (col1 == 1 && col2 == col1 + 2) {
                            return true;
                        }
                    }
                    return false;
                case "Rook":
                    if (col2 == col1 && (row2 <= 7 && row2 >= 0)) {
                        if (!isKill(gbArr,row2,col2,player)) {
                            if (isBlocking(p,gbArr,row1,row2,col1,col2)) return true;
                            else return false;
                        }
                    } else if (row2 ==row1 && (col2 <= 7 && col2 >= 0)) {
                        if (!isKill(gbArr,row2,col2,player)) {
                            if (isBlocking(p,gbArr,row1,row2,col1,col2)) return true;
                            else return false;
                        }
                    }
                    break;
                case "Knight":
                    if ( ((row2 == row1 + 1) || (row2 == row1 - 1) && (row2 <= 7 && row2 >= 0) )) {
                        if ( ((col2 == col1 + 2) || (col2 == col1 - 2) && (col2 <=7 && col2 >= 0)) ) {
                            return true;
                        }
                    } else if ( ((row2 == row1 + 2) || (row2 == row1 - 2) && (row2 <= 7 && row2 >= 0) )) {
                        if ( ((col2 == col1 + 1) || (col2 == col1 - 1) && (col2 <=7 && col2 >= 0)) ) {
                            return true;
                        }
                    }
                case "Bishop":
                    if ( (((row2 - row1) / (col2 - col1)) == 1 || ((row2 - row1) / (col2 - col1)) == -1) && ((row2 <= 7 && row2 >= 0) || col2 <= 7 && col2 >= 0)) {
                        if (!isKill(gbArr,row2,col2,player)) {
                            if (isBlocking(p,gbArr,row1,row2,col1,col2)) return true;
                            else return false;
                        } else {
                            return true;
                        }
                    }
                case "King":
                    if ((( ((row2 - row1) / (col2 - col1)) == 1 || ((row2 - row1) / (col2 - col1)) == -1)) && (row2 - row1 == 1 || row2 - row1 == -1) ) {
                        if (((row2 <= 7 && row2 >= 0) || col2 <= 7 && col2 >= 0)) {
                            return true;
                        }

                    } else if ( ((col2 - col1 == 1) || (col2 - col1 == -1)) && row2 == row1) {
                        if (col2 <= 7 && col2 >= 0) {
                            return true;
                        }
                    } else if ( ((row2 - row1 == 1) || (row2 - row1 == -1) && col2 == col1)) {
                        if (row2 <= 7 && row2 >= 0) {
                            return true;
                        }
                    }
                case "Queen":

                    if (col2 == col1 && (row2 <= 7 && row2 >= 0)) {
                        if(!isKill(gbArr,row2,col2,player)) {
                            if (isBlocking(p,gbArr,row1,row2,col1,col2)) return true;
                        } else {
                            return true;
                        }
                    } else if (row2 == row1 && (col2 <= 7 && col2 >= 0)) {
                        if(!isKill(gbArr,row2,col2,player)) {
                            if (isBlocking(p,gbArr,row1,row2,col1,col2)) return true;
                        } else {
                            return true;
                        }
                    } else if ( (((row2 - row1) / (col2 - col1)) == 1 || ((row2 - row1) / (col2 - col1)) == -1) && ((row2 <= 7 && row2 >= 0) || col2 <= 7 && col2 >= 0)) {
                        if (!isKill(gbArr, row2, col2, player)) {
                            if (isBlocking(p, gbArr, row1, row2, col1, col2)) return true;
                        } else {
                            return true;
                        }
                    }
                    return false;
                default:
                    return false;
            }
        }

        return false;
    }

    // Determines if the valid move results in the killing of an enemy's piece
    public boolean isKill(Button[][] gbArr, int rFinal, int rCol, String player) {
        if (!(gbArr[rFinal][rCol].getText().equals(""))) {
            if (gbArr[rFinal][rCol].getText().substring(gbArr[rFinal][rCol].getText().length()-1, gbArr[rFinal][rCol].getText().length()).equals(player)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // Checks the game board to see if a winner is determined
    public boolean checkWin(Button[][] gbArr, String player) {
        for (int row = 0;row < 8;row++) {
            for (int col = 0;col < 8;col++) {
                if (player.equals("B")) {
                    if (gbArr[row][col].getText().equals("KingW"))
                        return false;
                } else {
                    if (gbArr[row][col].getText().equals("KingB"))
                        return false;
                }
            }
        }
        return true;
    }

    // Checks if a piece is in the wat of a move
    public boolean isBlocking(String piece, Button[][] gbArr, int row1, int row2, int col1, int col2) {
        switch (piece) {
            case "Rook":
                if (col2 == col1) {
                    if (row2 > row1) {
                       for (int i = row1; i < 8;i++) {
                           if (!(gbArr[i][col2].getText().equals(""))) return false;

                       }
                    } else if (row2 < row1) {
                        for (int i = row1;i >= row2;i--) {
                            if(!(gbArr[i][col2].getText().equals(""))) return false;

                        }
                    }
                } else if (row2 == row1) {
                    if (col2 > col1) {
                        for (int i = col1;i < 8;i++) {
                            if (!(gbArr[row2][i].getText().equals(""))) return false;
                        }
                    } else if (col2 < col1) {
                        for (int i = col1;i >= col2;i--) {
                            if (!(gbArr[row2][i].getText().equals(""))) return false;
                        }
                    }
                }
                return true;
            case "Bishop":
                int cRow = 0;
                int cCol = 0;
                // If in an upwards right or left direction
                if (col2 > col1) {
                    if (row2 > row1) {
                        cRow = row1;
                        cCol = col1;
                        while (cCol != col2) {
                            if (!(gbArr[cRow][cCol].getText().equals(""))) {
                                if (!(gbArr[cRow][cCol].getText().equals("BishopW")) && !(gbArr[cRow][cCol].getText().equals("BishopB"))) return false;
                            }
                            cRow++;
                            cCol++;
                        }
                    } else if (row2 < row1) {
                        cRow = row1;
                        cCol = col1;
                        while (cCol != col2) {
                            if (!(gbArr[cRow][cCol].getText().equals(""))) {
                                if (!(gbArr[cRow][cCol].getText().equals("BishopW")) && !(gbArr[cRow][cCol].getText().equals("BishopB"))) return false;
                            }
                            cRow--;
                            cCol++;
                        }
                    }
                } else if (col2 < col1) {
                    if (row2 > row1) {
                        cRow = row1;
                        cCol = col2;
                        while (cCol != col2) {
                            if(!(gbArr[cRow][cCol].getText().equals(""))) {
                                if (!(gbArr[cRow][cCol].getText().equals("BishopW")) && !(gbArr[cRow][cCol].getText().equals("BishopB"))) return false;
                            }
                            cRow++;
                            cCol--;
                        }
                    } else if (row2 < row1) {
                        cRow = row2;
                        cCol = col2;
                        while (cCol != col2) {
                            if(!(gbArr[cRow][cCol].getText().equals(""))) {
                                if (!(gbArr[cRow][cCol].getText().equals("BishopW")) && !(gbArr[cRow][cCol].getText().equals("BishopB"))) return false;
                            }
                            cRow--;
                            cCol--;
                        }
                    }
                }
                return true;
            case "Queen":
                int cRowQ = 0;
                int cColQ = 0;
                // If in an upwards right or left direction
                if (col2 > col1) {
                    if (row2 > row1) {
                        cRowQ = row1;
                        cColQ = col1;
                        while (cColQ != col2) {
                            if (!(gbArr[cRowQ][cColQ].getText().equals(""))) {
                                if (!(gbArr[cRowQ][cColQ].getText().equals("BishopW")) && !(gbArr[cRowQ][cColQ].getText().equals("BishopB"))) return false;
                            }
                            cRowQ++;
                            cColQ++;
                        }
                    } else if (row2 < row1) {
                        cRowQ = row1;
                        cColQ = col1;
                        while (cColQ != col2) {
                            if (!(gbArr[cRowQ][cColQ].getText().equals(""))) {
                                if (!(gbArr[cRowQ][cColQ].getText().equals("BishopW")) && !(gbArr[cRowQ][cColQ].getText().equals("BishopB"))) return false;
                            }
                            cRowQ--;
                            cColQ++;
                        }
                    }
                } else if (col2 < col1) {
                    if (row2 > row1) {
                        cRowQ = row1;
                        cColQ = col2;
                        while (cColQ != col2) {
                            if(!(gbArr[cRowQ][cColQ].getText().equals(""))) {
                                if (!(gbArr[cRowQ][cColQ].getText().equals("BishopW")) && !(gbArr[cRowQ][cColQ].getText().equals("BishopB"))) return false;
                            }
                            cRowQ++;
                            cColQ--;
                        }
                    } else if (row2 < row1) {
                        cRowQ = row2;
                        cColQ = col2;
                        while (cColQ != col2) {
                            if(!(gbArr[cRowQ][cColQ].getText().equals(""))) {
                                if (!(gbArr[cRowQ][cColQ].getText().equals("BishopW")) && !(gbArr[cRowQ][cColQ].getText().equals("BishopB"))) return false;
                            }
                            cRowQ--;
                            cColQ--;
                        }
                    }
                }
                if (col2 == col1) {
                    if (row2 > row1) {
                        for (int i = row1+1;i <= row2;i++) {
                            if (!(gbArr[i][col2].getText().equals(""))) return false;
                        }
                    } else {
                        for (int i = row1-1;i >= row2;i--) {
                            if (!(gbArr[i][col2].getText().equals(""))) return false;
                        }
                    }
                } else if (row2 == row1) {
                    if (col2 > col1) {
                        for (int i = col1+1;i <= col2;i++) {
                            if (!(gbArr[row2][i].getText().equals(""))) return false;
                        }
                    } else {
                        for (int i = col1-1;i >= col2;i--) {
                            if (!(gbArr[row2][i].getText().equals(""))) return false;
                        }
                    }
                }
                return true;
            default:

        }
        return false;
    }


}
