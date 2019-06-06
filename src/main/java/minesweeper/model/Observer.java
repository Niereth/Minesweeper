package minesweeper.model;

import java.util.List;

public interface Observer {

    void fieldCreated(int totalCols, int totalRows, int amountOfMines);

    void cellsUpdated(List<Cell> cells);

    void gameStatusUpdated(GameStatus gameStatus);

    void minesLeftToFlagUpdated(int minesLeftToFlag);

    void gameLaunchedFirstTime();

    void timerLaunched();

    void timerStopped();

    void secondsPassedUpdated(int secondsPassed);

    void newRecordIsReadyToBeSaved(String championCategory);

    void newRecordIsSaved();

    void recordsUpdated(Champion[] champions);

    void noRecordSet();
}
