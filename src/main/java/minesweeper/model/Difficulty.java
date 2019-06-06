package minesweeper.model;

public enum Difficulty {
    BEGINNER(9, 9, 10),
    MEDIUM(16, 16, 40),
    EXPERT(30, 16, 99),
    CUSTOM;

    private int cols;
    private int rows;
    private int mines;

    Difficulty() {
    }

    Difficulty(int cols, int rows, int mines) {
        this.cols = cols;
        this.rows = rows;
        this.mines = mines;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public int getMines() {
        return mines;
    }
}