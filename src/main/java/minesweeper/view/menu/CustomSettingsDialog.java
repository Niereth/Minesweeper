package minesweeper.view.menu;

import minesweeper.controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;

class CustomSettingsDialog {
    private Controller controller;
    private JDialog dialog;

    CustomSettingsDialog(Controller controller) {
        this.controller = controller;
        initDialog();
    }

    private void initDialog() {
        JFrame frame;
        JPanel background;
        Box nameBox;
        Box fieldBox;

        frame = new JFrame();
        dialog = new JDialog(frame, "Особые настройки", true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        JLabel width = new JLabel("Длина (9-30):");
        JLabel height = new JLabel("Ширина (9-24):");
        JLabel mines = new JLabel("Мины (1-576):");

        JTextField widthField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        widthField.setColumns(3);
        widthField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (widthField.getText().length() >= 2) {
                    e.consume();
                }
            }
        });

        JTextField heightField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        heightField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (heightField.getText().length() >= 2) {
                    e.consume();
                }
            }
        });

        JTextField minesField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        minesField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (minesField.getText().length() >= 3) {
                    e.consume();
                }
            }
        });

        JButton acceptButton = new JButton("OK");
        acceptButton.addActionListener(e -> {
            Object colsValue = ((JFormattedTextField) widthField).getValue();
            Object rowsValue = ((JFormattedTextField) heightField).getValue();
            Object minesValue = ((JFormattedTextField) minesField).getValue();

            if (colsValue != null && rowsValue != null && minesValue != null) {
                int totalCols = Integer.parseInt(colsValue.toString());
                int totalRows = Integer.parseInt(rowsValue.toString());
                int amountOfMines = Integer.parseInt(minesValue.toString());
                if (totalCols > 0 && totalRows > 0 && amountOfMines > 0) {
                    controller.startNewCustomGame(totalCols, totalRows, amountOfMines);
                    dialog.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(new JPanel(), "Можно вводить только положительные числа!", "Ошибка!", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(new JPanel(), "Можно вводить только числа!", "Ошибка!", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Отмена");
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(e -> dialog.setVisible(false));

        nameBox = new Box(BoxLayout.Y_AXIS);
        nameBox.add(width);
        nameBox.add(height);
        nameBox.add(mines);

        fieldBox = new Box(BoxLayout.Y_AXIS);
        fieldBox.add(widthField);
        fieldBox.add(heightField);
        fieldBox.add(minesField);

        background = new JPanel();
        background.setBorder(new EmptyBorder(5, 5, 5, 5));
        background.add(BorderLayout.WEST, nameBox);
        background.add(BorderLayout.EAST, fieldBox);
        background.add(BorderLayout.SOUTH, acceptButton);
        background.add(BorderLayout.SOUTH, cancelButton);
        background.setPreferredSize(new Dimension(170, 110));

        dialog.add(background);
        dialog.pack();
        dialog.setResizable(false);
    }

    void openDialog(JMenuBar whereToOpen) {
        dialog.setLocationRelativeTo(whereToOpen);
        dialog.setVisible(true);
    }
}
