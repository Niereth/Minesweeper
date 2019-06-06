package minesweeper.controller;

import minesweeper.model.*;

public class Controller {
    private ModelField model;
    private GameTimer timer;

    public Controller(ModelField m) {
        model = m;
        timer = new GameTimer(m);
    }

    public void clickLeftMouse(int x, int y) {
        model.openCell(x, y);
    }

    public void clickRightMouse(int x, int y) {
        model.setNextCellStatus(x, y);
    }

    public void startNewGame(Difficulty difficulty) {
        model.startNewGame(difficulty);
    }

    public void startNewCustomGame(int totalCols, int totalRows, int amountOfMines) {
        model.startNewCustomGame(totalCols, totalRows, amountOfMines);
    }

    public void restartGame() {
        model.createField();
    }

    public void clickMenuExit() {
        System.exit(0);
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void saveRecord(String nickName) {
        model.saveRecord(nickName);
    }

    public void resetResults() {
        model.resetResults();
    }

    public void loadRecords() {
        model.loadRecords();
    }
}