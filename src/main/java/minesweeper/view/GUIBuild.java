package minesweeper.view;

import minesweeper.controller.Controller;
import minesweeper.model.*;
import minesweeper.view.images.SmileyImages;
import minesweeper.view.menu.ChampionsDialog;
import minesweeper.view.menu.GUIMenu;
import minesweeper.view.records.DialogOfNewChampion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class GUIBuild implements Observer {
    private JFrame frame;
    private GUIField guiField;
    private Controller controller;
    private int amountOfMines;
    private JButton restart;
    private JLabel minesLabel;
    private JLabel timerLabel;
    private boolean firstLaunch;

    public GUIBuild(Controller controller) {
        frame = new JFrame();
        guiField = new GUIField(controller);
        this.controller = controller;
        SmileyImages.setImages();
        initFrame();
    }

    private void initFrame() {
        GUIMenu menu = new GUIMenu(controller);
        frame.setJMenuBar(menu.getMenuBar());
        frame.setTitle("Сапёр");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void startNewGame() {
        guiField.createField();
        initPanels();
    }

    private void initPanels() {
        JPanel topPanel = new JPanel();
        topPanel.setBorder(new EmptyBorder(3,3,3,3));
        minesLabel = new JLabel("Мины: " + amountOfMines);

        restart = new JButton();
        restart.setPreferredSize(new Dimension(40, 40));
        restart.setIcon(SmileyImages.SMILEY_SMILE.getIcon());
        restart.addActionListener(e -> controller.restartGame());
        timerLabel = new JLabel("Время: 0 c.");

        topPanel.add(BorderLayout.WEST, minesLabel);
        topPanel.add(BorderLayout.CENTER, restart);
        topPanel.add(BorderLayout.EAST, timerLabel);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(BorderLayout.CENTER, guiField.getFieldGrid());
        frame.getContentPane().add(BorderLayout.NORTH, topPanel);
        frame.pack();
        if (firstLaunch) {
            frame.setLocationRelativeTo(null);
            firstLaunch = false;
        }
    }

    private void showWinnerMessage() {
        JOptionPane.showMessageDialog(new JPanel(), "Поздравляем, вы выиграли!" + System.lineSeparator()
                + "Ставки на спорт один икс бэт...", "Победа!",JOptionPane.PLAIN_MESSAGE);
    }

    private void showLoserMessage() {
        JOptionPane.showMessageDialog(new JPanel(), "К сожалению, вы проиграли!", "Поражение", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void fieldCreated(int totalCols, int totalRows, int amountOfMines) {
        guiField.setTotalCols(totalCols);
        guiField.setTotalRows(totalRows);
        this.amountOfMines = amountOfMines;
        startNewGame();
    }

    @Override
    public void cellsUpdated(List<Cell> cells) {
        for (Cell cellToUpdate : cells) {
            guiField.drawCell(cellToUpdate);
        }
    }

    @Override
    public void gameStatusUpdated(GameStatus gameStatus) {
        if (gameStatus == GameStatus.VICTORY) {
            controller.stopTimer();
            restart.setIcon(SmileyImages.SMILEY_WINNER.getIcon());
        } else if (gameStatus == GameStatus.DEFEAT){
            controller.stopTimer();
            restart.setIcon(SmileyImages.SMILEY_LOSER.getIcon());
            showLoserMessage();
        }
    }

    @Override
    public void minesLeftToFlagUpdated(int minesLeftToFlag) {
        minesLabel.setText("Мины: " + minesLeftToFlag);
        minesLabel.repaint();
    }

    @Override
    public void gameLaunchedFirstTime() {
        firstLaunch = true;
    }

    @Override
    public void timerLaunched() {
        controller.startTimer();
    }

    @Override
    public void timerStopped() {
        controller.stopTimer();
    }

    @Override
    public void secondsPassedUpdated(int secondsPassed) {
        timerLabel.setText("Время: " + secondsPassed + " c." );
    }

    @Override
    public void newRecordIsReadyToBeSaved(String championCategory) {
        DialogOfNewChampion newChampion = new DialogOfNewChampion(championCategory);
        newChampion.openDialog();
        if (newChampion.isConfirmed()) {
            controller.saveRecord(newChampion.getNickName());
        }
    }

    @Override
    public void newRecordIsSaved() {
        ChampionsDialog championsDialog = new ChampionsDialog(controller);
        championsDialog.openDialog(new JMenuBar());
    }

    @Override
    public void recordsUpdated(Champion[] champions) {
        ChampionsDialog.setChampions(champions);
    }

    @Override
    public void noRecordSet() {
        showWinnerMessage();
    }
}