package minesweeper.view;

import minesweeper.controller.Controller;
import minesweeper.model.Cell;
import minesweeper.view.images.CellImages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIField {
    private JButton[][] cells;
    private Container grid;
    private int totalCols;
    private int totalRows;
    private static final int CELL_SIZE = 25;
    private Controller controller;

    GUIField(Controller controller) {
        this.controller = controller;
        CellImages.setImages();
    }

    void createField() {
        cells = new JButton[totalRows][totalCols];
        grid = new Container();
        grid.setLayout(new GridLayout(totalRows, totalCols));

        for (int y = 0; y < totalRows; y++) {
            for (int x = 0; x < totalCols; x++) {
                cells[y][x] = new JButton();
                cells[y][x].setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                cells[y][x].setIcon(CellImages.CLOSED.getIcon());
                cells[y][x].addMouseListener(new CellListener());
                grid.add(cells[y][x]);
            }
        }
    }

    void drawCell(Cell cell) {
        cells[cell.getRow()][cell.getCol()].setIcon(chooseImageToDraw(cell));
        cells[cell.getRow()][cell.getCol()].updateUI();
    }

    private ImageIcon chooseImageToDraw(Cell cell) {
        switch (cell.getCellStatus()) {
            case OPENED:
                switch (cell.getMineStatus()) {
                    case MINED:
                        return CellImages.MINE.getIcon();
                    case EXPLODED:
                        return CellImages.MINE_EXPLODED.getIcon();
                    case MISTAKEN:
                        return CellImages.MINE_MISTAKE.getIcon();
                    case NOTMINED:
                        return CellImages.values()[cell.getAmountOfNearMines()].getIcon();
                    default:
                        return null;
                }
            case CLOSED:
                return CellImages.CLOSED.getIcon();
            case FLAGGED:
                return CellImages.CLOSED_FLAG.getIcon();
            case QUESTIONED:
                return CellImages.CLOSED_QUESTION.getIcon();
            default:
                return null;
        }
    }

    Container getFieldGrid() {
        return grid;
    }

    void setTotalCols(int totalCols) {
        this.totalCols = totalCols;
    }

    void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public static int getCellSize() {
        return CELL_SIZE;
    }

    class CellListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            for (int i = 0; i < totalCols; i++) {
                for (int j = 0; j < totalRows; j++) {
                    if (cells[j][i] == e.getSource()) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            controller.clickLeftMouse(i, j);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            controller.clickRightMouse(i, j);
                        }
                    }
                }
            }
        }
    }
}