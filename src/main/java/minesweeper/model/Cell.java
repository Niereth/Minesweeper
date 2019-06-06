package minesweeper.model;

public class Cell {
    private int col;
    private int row;
    private int amountOfNearMines;
    private CellStatus cellStatus;
    private MineStatus mineStatus;

    Cell(int x, int y) {
        col = x;
        row = y;
        cellStatus = CellStatus.CLOSED;
        mineStatus = MineStatus.NOTMINED;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public MineStatus getMineStatus() {
        return mineStatus;
    }

    void setMineStatus(MineStatus mineStatus) {
        this.mineStatus = mineStatus;
    }

    void setNextStatus() {
        String[] statusArr = {"CLOSED", "FLAGGED", "QUESTIONED"};
        int id = 0;
        for (int i = 0; i < statusArr.length; i++) {
            if (cellStatus.toString().equals(statusArr[i])) {
                id = i;
                break;
            }
        }
        cellStatus = CellStatus.valueOf(statusArr[(id + 1) % statusArr.length]);
    }

    boolean canOpen() {
        return cellStatus == CellStatus.CLOSED || cellStatus == CellStatus.QUESTIONED;
    }

    void setOpened() {
        cellStatus = CellStatus.OPENED;
    }

    void setMined() {
        mineStatus = MineStatus.MINED;
    }

    public int getAmountOfNearMines() {
        return amountOfNearMines;
    }

    void setAmountOfNearMines(int amountOfNearMines) {
        this.amountOfNearMines = amountOfNearMines;
    }
}