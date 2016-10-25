package com.example.kevin.senku;

public class SenkuTable {
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
        //Solo se mueve en un eje
        if (srcX != destX && srcY != destY) {
            return false;
        } else {
            //Origen activado y destino desactivado
            if (table[srcX][srcY] == 1 && table[destX][destY] == 0) {
                //Movimiento horizontal
                if (srcX != destX) {
                    //Derecha
                    if (srcX < destX) {
                        //Medio activado
                        if (srcX == destX - 2 && table[srcX + 1][srcY] == 1) {
                            return true;
                        }
                    //Izquierda
                    } else {
                        //Medio activado
                        if (destX == srcX - 2 && table[srcX - 1][srcY] == 1) {
                            return true;
                        }
                    }
                //Movimieto vertical
                } else {
                    //Abajo
                    if (srcY < destY) {
                        //Medio activado
                        if (srcY == destY - 2 && table[srcX][srcY + 1] == 1) {
                            return true;
                        }
                    //Arriba
                    } else {
                        //Medio activado
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
                System.out.println(active);
                return "" + (srcX + 1) + "-" + srcY;
            } else {
                table[srcX-1][srcY] = 0;
                active--;
                System.out.println(active);
                return "" + (srcX - 1) + "-" + srcY;
            }
        } else {
            if (srcY < destY) {
                table[srcX][srcY+1] = 0;
                active--;
                System.out.println(active);
                return "" + srcX + "-" + (srcY + 1);
            } else {
                table[srcX][srcY-1] = 0;
                active--;
                System.out.println(active);
                return "" + destX + "-" + (srcY - 1);
            }
        }
    }

    public boolean isFinished() {
        return active == 1;
    }
}