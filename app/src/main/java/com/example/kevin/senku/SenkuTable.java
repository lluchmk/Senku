package com.example.kevin.senku;

public class SenkuTable {

    public static final int COMPLETED_WIN = 0;
    public static final int COMPLETED_LOSE = 1;
    public static final int NOT_COMPLETED = 2;

    private final int[][] table = new int[7][7];
    private int active = 0;

    public SenkuTable() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if ((i < 2 && j < 2) || (i > 4 && j < 2)
                        || (i < 2 && j > 4) || (i > 4 && j > 4)) {
                    table[i][j] = -1;
                } else {
                    table[i][j] = 1;
                    active++;
                }
            }
        }
        table[3][3] = 0;
        active--;
        System.out.println(active);
    }

    public boolean isValidMove(int srcX, int srcY, int destX, int destY) {
        //Check if moving on one axis
        if (srcX != destX && srcY != destY) {
            return false;
        } else {
            //Origin checked and destiny unchecked
            if (table[srcX][srcY] == 1 && table[destX][destY] == 0) {
                //Horizontal move
                if (srcX != destX) {
                    //Right
                    if (srcX < destX) {
                        //Middle button checked
                        if (srcX == destX - 2 && table[srcX + 1][srcY] == 1) {
                            return true;
                        }
                    //Left
                    } else {
                        //Middle button checked
                        if (destX == srcX - 2 && table[srcX - 1][srcY] == 1) {
                            return true;
                        }
                    }
                //Vertical move
                } else {
                    //Down
                    if (srcY < destY) {
                        //Middle button checked
                        if (srcY == destY - 2 && table[srcX][srcY + 1] == 1) {
                            return true;
                        }
                    //UP
                    } else {
                        //Middle button checked
                        if (destY == srcY - 2 && table[srcX][srcY - 1] == 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public String move(int srcX, int srcY, int destX, int destY) {
        table[srcX][srcY] = 0;
        table[destX][destY] = 1;
        if (srcX != destX) {
            if (srcX < destX) {
                table[srcX+1][srcY] = 0;
                active--;
                return "" + (srcX + 1) + "-" + srcY;
            } else {
                table[srcX-1][srcY] = 0;
                active--;
                return "" + (srcX - 1) + "-" + srcY;
            }
        } else {
            if (srcY < destY) {
                table[srcX][srcY+1] = 0;
                active--;
                return "" + srcX + "-" + (srcY + 1);
            } else {
                table[srcX][srcY-1] = 0;
                active--;
                return "" + destX + "-" + (srcY - 1);
            }
        }
    }

    public int isFinished() {
        if (active > 1) {
            return NOT_COMPLETED;
        } else if (active == 1 && table[3][3] == 1) {
            return COMPLETED_WIN;
        } else {
            return COMPLETED_LOSE;
        }
    }
}