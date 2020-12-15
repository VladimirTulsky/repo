package lesson4;

import java.util.Random;
import java.util.Scanner;

public class Cross {

    static int SIZE_X = 5;
    static int SIZE_Y = 5;
    static int WIN_COMBINATION = 4;

    static char[][] field = new char[SIZE_Y][SIZE_X];
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    static char PLAYER_DOT = 'X';
    static char AI_DOT = 'O';
    static char EMPTY_DOT = '.';

    static void initField() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    static void printField() {
        System.out.println("-------");
        for (int i = 0; i < SIZE_Y; i++) {
            System.out.print("|");
            for (int j = 0; j < SIZE_X; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println("-------");
    }

    static boolean isCellValid(int y, int x) {
        if (x < 0 || y < 0 || x > SIZE_X - 1 || y > SIZE_Y - 1) {
            return false;
        }
        return field[y][x] == EMPTY_DOT;
    }

    static boolean isFieldFull() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (field[i][j] == EMPTY_DOT) {
                    return false;
                }
            }
        }
        return true;
    }

    static void setSym(int y, int x, char sym) {
        field[y][x] = sym;
    }

    static void playerStep() {
        int x,y;
        do {
            System.out.println("Введите координаты: X Y (1-5)");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y,x));
        setSym(y,x, PLAYER_DOT);
    }

    private static void aiStep() {
        int x, y;
        //ход на победу
        if(aiStepCheck(1, AI_DOT)) return;
        //блокировка победы игрока
        if(aiStepCheck(1, PLAYER_DOT)) return;
        //увеличение серии 0 подряд
        if(aiStepCheck(2, AI_DOT)) return;
        if(aiStepCheck(3, AI_DOT)) return;
        do {
            x = random.nextInt(SIZE_X);
            y = random.nextInt(SIZE_Y);
        } while (!isCellValid(y, x));
        System.out.println("Ход компьютера");
        setSym(y, x, AI_DOT);
    }
    //проверка на ячейку для хода
    private static boolean aiStepCheck(int step, char sym) {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if(i + WIN_COMBINATION <= SIZE_Y) {
                    if(checkVertical(i, j, sym) == WIN_COMBINATION - step) {
                        if(aiBlockVertical(i, j)) return true;
                    }

                }
                if(j + WIN_COMBINATION <= SIZE_X) {
                    if (checkHorisontal(i, j, sym) == WIN_COMBINATION - step) {
                        if(aiBlockHorizontal(i, j)) return true;
                    }

                    if(i + WIN_COMBINATION <= SIZE_Y) {
                        if (checkDiagonal1(i, j, sym) == WIN_COMBINATION - step) {
                            if (aiBlockDiagonal1(i, j)) return true;
                        }
                    }
                    if(i - WIN_COMBINATION > -2) {
                        if (checkDiagonal2(i, j, sym) == WIN_COMBINATION - step) {
                            if (aiBlockDiagonal2(i, j)) return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean aiBlockHorizontal(int y, int x) {
        for (int i = x; i < WIN_COMBINATION; i++) {
            if(field[y][i] == EMPTY_DOT) {
                field[y][i] = AI_DOT;
                System.out.println("Ход компьютера");
                return true;
            }
        }
        return false;

    }
    private static boolean aiBlockVertical(int y, int x) {
        for (int i = y; i < WIN_COMBINATION; i++) {
            if(field[i][x] == EMPTY_DOT) {
                field[i][x] = AI_DOT;
                System.out.println("Ход компьютера");
                return true;
            }
        }
        return false;
    }

    private static boolean aiBlockDiagonal1(int y, int x) {
        for (int i = 0; i < WIN_COMBINATION; i++) {
            if(field[y + i][x + i] == EMPTY_DOT) {
                field[y + i][x + i] = AI_DOT;
                System.out.println("Ход компьютера");
                return true;
            }
        }
        return false;
    }

    private static boolean aiBlockDiagonal2(int y, int x) {
        for (int i = 0; i < WIN_COMBINATION; i++) {
            if(field[y - i][x + i] == EMPTY_DOT) {
                field[y - i][x + i] = AI_DOT;
                System.out.println("Ход компьютера");
                return true;
            }
        }
        return false;
    }

    private static boolean checkWin(char sym) {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if(i + WIN_COMBINATION <= SIZE_Y) {
                    if(checkVertical(i, j, sym) >= WIN_COMBINATION) return true;
                }
                if(j + WIN_COMBINATION <= SIZE_X) {
                    if(checkHorisontal(i, j, sym) >= WIN_COMBINATION) return true;
                    if(i + WIN_COMBINATION <= SIZE_Y) {
                        if(checkDiagonal1(i, j, sym) >= WIN_COMBINATION) return true;
                    }
                    if(i - WIN_COMBINATION > -2) {
                        if(checkDiagonal2(i, j, sym) >= WIN_COMBINATION) return true;
                    }
                }
            }
        }
        return false;
    }
    //проверка по горизонтали
    private static int checkHorisontal(int y, int x, char sym) {
        int counter = 0;
        for (int i = x; i < WIN_COMBINATION + x; i++) {
            if((field[y][i]) == sym) counter++;
        }
        return counter;
    }
    //проверка по вертикали
    private static int checkVertical(int y, int x, char sym) {
        int counter = 0;
        for (int i = y; i < WIN_COMBINATION + y; i++) {
            if((field[i][x]) == sym) counter++;
        }
        return counter;
    }
    //проверка по диагонали 1
    private static int checkDiagonal1(int y, int x, char sym) {
        int counter = 0;
        for (int i = 0; i < WIN_COMBINATION; i++) {
            if((field[i + y][i + x]) == sym) counter++;
        }
        return counter;
    }
    //проверка по диагонали 2
    private static int checkDiagonal2(int y, int x, char sym) {
        int counter = 0;
        for (int i = 0; i < WIN_COMBINATION; i++) {
            if((field[y - i][x + i]) == sym) counter++;
        }
        return counter;
    }


    public static void main(String[] args) {
        initField();
        printField();

        while (true) {
            playerStep();
            printField();
            if (checkWin(PLAYER_DOT)) {
                System.out.println("Player WIN!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("DRAW");
                break;
            }
            aiStep();
            printField();
            if (checkWin(AI_DOT)) {
                System.out.println("WIN SkyNet");
                break;
            }
            if (isFieldFull()) {
                System.out.println("DRAW");
                break;
            }
        }


    }
}
