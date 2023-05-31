package ru.gb.s2;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int WIN_COUNT = 4;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '*';
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random random = new Random();
    private static char[][] field; // Хранит текущее состояние игрового поля
    private static int fieldSizeX; // Размерность поля
    private static int fieldSizeY; // Размерность поля

    public static void main(String[] args) {
        initialize();
        printField();
        while (true) {
            humanTurn();
            System.out.println();
            printField();
            if (gameCheck(DOT_HUMAN, "YOU WIN!")) {
                break;
            }
            aiTurn();
            System.out.println();
            printField();
            if (gameCheck(DOT_AI, "BOT WIN!")) {
                break;
            }
        }
    }

    private static void initialize() {
        fieldSizeY = 5;
        fieldSizeX = 5;
        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++) {
            System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
        }
        System.out.println();

        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeY; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
        for (int i = 0; i < fieldSizeX * 2 + 2; i++) {
            System.out.print("-");
        }
    }

    private static void humanTurn() {
        int x, y;
        do {
            System.out.println("Enter coordinates X Y from 1 to 5");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }

    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    private static void aiTurn() {
        int x, y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

    static boolean checkWin(char c, int winCount,char[][]board) {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (board[x][y] != DOT_EMPTY) {
                    int n = x;
                    int m = y;
                    int count = 0;
                    while (m < fieldSizeY) {
                        if (board[n][m] == c && isCellValid(n, m)) {
                            count++;
                        }else break;
                        m++;
                    }
                    if (count == winCount) return true;
                    n = x;
                    m = y;
                    count = 0;
                    while (m < fieldSizeY && n < fieldSizeX) {
                        if (board[n][m] == c && isCellValid(n, m)) {
                            count++;
                        }else break;
                        n++;
                        m++;
                    }
                    if (count == winCount) return true;
                    n = x;
                    m = y;
                    count = 0;
                    while (n < fieldSizeX) {
                        if (board[n][m] == c && isCellValid(n, m)) {
                            count++;
                        }else break;
                        n++;
                    }
                    if (count == winCount) return true;
                    n = x;
                    m = y;
                    count = 0;
                    while (n < fieldSizeX && m >= 0) {
                        if (board[n][m] == c && isCellValid(n, m)) {
                            count++;
                        }else break;
                        n++;
                        m--;
                    }
                    if (count == winCount) return true;
                }
            }
        }
        return false;
    }

    static boolean checkDraw() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    static boolean gameCheck(char c, String str) {
        if (checkWin(c,WIN_COUNT,field)) {
            System.out.println(str);
            return true;
        }
        if (checkDraw()) {
            System.out.println("Draw!");
            return true;
        }
        return false; // Игра продолжается
    }

    private static void mediumAi(){
        char[][] copy = field.clone();
        int x, y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        if (checkWin(DOT_HUMAN,WIN_COUNT-1,copy)){
            // TODO: 31.05.2023 найти нужный ход, в случае обнаружения 3-х фишек противника подряд! 
        }
        field[x][y] = DOT_AI;
    }
}