package minesweeper.model;

import java.util.*;

public class ModelField implements Observed {
    private Cell[][] cells;
    private GameStatus gameStatus;
    private Difficulty difficulty;
    private int totalCols;
    private int totalRows;
    private int amountOfMines;
    private int closedCellsLeftToOpen;
    private int minesLeftToFlag;
    private boolean isFirstStep;
    private int secondsPassed;
    private Random random;
    private ArrayList<Cell> updatedCells;
    private List<Observer> observers;
    private Records records;

    public ModelField() {
        random = new Random();
        updatedCells = new ArrayList<>();
        observers = new ArrayList<>();
        records = new Records(observers);
    }

    public void startNewGame(Difficulty difficulty) {
        this.difficulty = difficulty;
        totalCols = difficulty.getCols();
        totalRows = difficulty.getRows();
        amountOfMines = difficulty.getMines();
        createField();
    }

    public void startNewCustomGame(int totalCols, int totalRows, int amountOfMines) {
        difficulty = Difficulty.CUSTOM;
        checkFieldParams(totalCols, totalRows, amountOfMines);
        createField();
    }

    private void checkFieldParams(int totalCols, int totalRows, int amountOfMines) {
        final int minFieldWidth = 9;
        final int maxFieldWidth = 30;
        this.totalCols = validateParameters(totalCols, minFieldWidth, maxFieldWidth);

        final int minFieldHeight = 9;
        final int maxFieldHeight = 24;
        this.totalRows = validateParameters(totalRows, minFieldHeight, maxFieldHeight);

        final double minesRatio = 0.8;
        final int minAmountOfMines = 1;
        final int maxAmountOfMines = (int) (this.totalCols * this.totalRows * minesRatio);
        this.amountOfMines = validateParameters(amountOfMines, minAmountOfMines, maxAmountOfMines);
    }

    private int validateParameters(int source, int minValue, int maxValue) {
        if (source < minValue) {
            source = minValue;
        } else if (source > maxValue) {
            source = maxValue;
        }
        return source;
    }

    public void createField() {
        notifyTimerStopped();
        closedCellsLeftToOpen = totalCols * totalRows - amountOfMines;
        minesLeftToFlag = amountOfMines;
        gameStatus = GameStatus.RUNNING;
        isFirstStep = true;

        cells = new Cell[totalRows][totalCols];
        for (int y = 0; y < totalRows; y++) {
            for (int x = 0; x < totalCols; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }

        placeMines();
        placeNumbersAroundCells();

        notifyFieldCreated();
        notifyMinesLeftToFlagUpdated();
    }

    private void placeMines() {
        boolean[] minesArr = new boolean[totalCols * totalRows];
        for (int i = 0; i < amountOfMines; i++) {
            minesArr[i] = true;
        }
        shuffleArray(minesArr);

        int index = 0;
        for (int y = 0; y < totalRows; y++) {
            for (int x = 0; x < totalCols; x++) {
                if (minesArr[index++]) {
                    cells[y][x].setMined();
                }
            }
        }
    }

    private void shuffleArray(boolean[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            int index = random.nextInt(i + 1);
            boolean tmp = arr[index];
            arr[index] = arr[i];
            arr[i] = tmp;
        }
    }

    private void placeNumbersAroundCells() {
        for (int y = 0; y < totalRows; y++) {
            for (int x = 0; x < totalCols; x++) {
                int amount = countMinesAroundCell(cells[y][x]);
                cells[y][x].setAmountOfNearMines(amount);
            }
        }
    }

    private int countMinesAroundCell(Cell centerCell) {
        ArrayList<Cell> cellsAround = getCellsAround(centerCell);
        int counter = 0;
        for (Cell cell : cellsAround) {
            if (cell.getMineStatus() == MineStatus.MINED) {
                counter++;
            }
        }
        return counter;
    }

    private ArrayList<Cell> getCellsAround(Cell centerCell) {
        ArrayList<Cell> cellsAround = new ArrayList<>();
        int x = centerCell.getCol();
        int y = centerCell.getRow();
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (isInRange(i, j) && (cells[i][j].canOpen())) {
                    cellsAround.add(cells[i][j]);
                }
            }
        }
        return cellsAround;
    }

    private boolean isInRange(int col, int row) {
        return row >= 0 && row < totalCols &&
                col >= 0 && col < totalRows;
    }

    public void openCell(int x, int y) {
        if (gameStatus != GameStatus.RUNNING) {
            return;
        }
        if (!cells[y][x].canOpen()) {
            return;
        }
        if (isFirstStep) {
            notifyTimerLaunched();
            isFirstStep = false;
        }

        updatedCells.clear();
        cells[y][x].setOpened();
        closedCellsLeftToOpen--;

        if (cells[y][x].getMineStatus() == MineStatus.MINED) {
            cells[y][x].setMineStatus(MineStatus.EXPLODED);
            gameStatus = GameStatus.DEFEAT;
            updatedCells.add(cells[y][x]);

            openAllMines();

            notifyCellsUpdated(updatedCells);
            notifyGameStatusUpdated();
        } else {
            tryToOpenCellsAround(cells[y][x]);
            notifyCellsUpdated(updatedCells);
            checkGameStatus();
        }
    }

    private void openAllMines() {
        for (int y = 0; y < totalRows; y++) {
            for (int x = 0; x < totalCols; x++) {
                if (cells[y][x].getCellStatus() == CellStatus.FLAGGED && cells[y][x].getMineStatus() == MineStatus.NOTMINED) {
                    cells[y][x].setMineStatus(MineStatus.MISTAKEN);
                    cells[y][x].setOpened();
                    updatedCells.add(cells[y][x]);
                }
                if (cells[y][x].getMineStatus() == MineStatus.MINED && cells[y][x].canOpen()) {
                    cells[y][x].setOpened();
                    updatedCells.add(cells[y][x]);
                }
            }
        }
    }

    private void tryToOpenCellsAround(Cell centerCell) {
        if (centerCell.canOpen()) {
            centerCell.setOpened();
            closedCellsLeftToOpen--;
        }

        updatedCells.add(centerCell);

        if (centerCell.getAmountOfNearMines() == 0) {
            ArrayList<Cell> cellsAround = getCellsAround(centerCell);
            for (Cell cell : cellsAround) {
                tryToOpenCellsAround(cell);
            }
        }
    }

    public void setNextCellStatus(int x, int y) {
        if (gameStatus != GameStatus.RUNNING) {
            return;
        }
        if (cells[y][x].getCellStatus() == CellStatus.OPENED) {
            return;
        }
        updatedCells.clear();
        cells[y][x].setNextStatus();
        if (cells[y][x].getCellStatus() == CellStatus.FLAGGED) {
            minesLeftToFlag--;
        } else if (cells[y][x].getCellStatus() == CellStatus.QUESTIONED){
            minesLeftToFlag++;
        }
        updatedCells.add(cells[y][x]);
        notifyCellsUpdated(updatedCells);
        notifyMinesLeftToFlagUpdated();
        checkGameStatus();
    }

    private void checkGameStatus() {
        if (closedCellsLeftToOpen == 0 && minesLeftToFlag == 0) {
            gameStatus = GameStatus.VICTORY;
            notifyGameStatusUpdated();
            records.checkIsRecord(difficulty, secondsPassed);
        }
    }

    public void updateSecondsPassed(int secondsPassed) {
        this.secondsPassed = secondsPassed;
        notifySecondsPassedUpdated();
    }

    public void saveRecord(String nickName) {
        records.saveRecord(secondsPassed, nickName);
    }

    public void resetResults() {
        records.createDefaultRecords();
    }

    public void loadRecords() {
        records.loadRecords();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyFieldCreated() {
        for (Observer observer : observers) {
            observer.fieldCreated(totalCols, totalRows, amountOfMines);
        }
    }

    private void notifyCellsUpdated(List<Cell> cells) {
        for (Observer observer : observers) {
            observer.cellsUpdated(cells);
        }
    }

    private void notifyGameStatusUpdated() {
        for (Observer observer : observers) {
            observer.gameStatusUpdated(gameStatus);
        }
    }

    private void notifyMinesLeftToFlagUpdated() {
        for (Observer observer : observers) {
            observer.minesLeftToFlagUpdated(minesLeftToFlag);
        }
    }

    private void notifyTimerLaunched() {
        for (Observer observer : observers) {
            observer.timerLaunched();
        }
    }

    private void notifyTimerStopped() {
        for (Observer observer : observers) {
            observer.timerStopped();
        }
    }

    private void notifySecondsPassedUpdated() {
        for (Observer observer : observers) {
            observer.secondsPassedUpdated(secondsPassed);
        }
    }
}