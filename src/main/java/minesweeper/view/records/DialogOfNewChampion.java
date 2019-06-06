package minesweeper.view.records;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DialogOfNewChampion {
    private String nickName;
    private boolean confirmation;
    private JDialog dialog;

    public DialogOfNewChampion(String championCategory) {
        initDialog(championCategory);
    }

    private void initDialog(String championCategory) {
        JFrame frame;
        JPanel background;

        frame = new JFrame();
        dialog = new JDialog(frame);
        dialog.setModal(true);

        JLabel messageLine1 = new JLabel("Вы стали чемпионом");
        JLabel messageLine2 = new JLabel("среди " + championCategory + ".");
        JLabel messageLine3 = new JLabel("Введите свое имя.");
        JTextField nickNameField = new JTextField("Аноним", 15);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            nickName = nickNameField.getText();
            confirmation = true;
            dialog.setVisible(false);
        });

        background = new JPanel();
        background.setBorder(new EmptyBorder(3, 3, 3, 3));
        background.add(messageLine1);
        background.add(messageLine2);
        background.add(messageLine3);
        background.add(nickNameField);
        background.add(okButton);
        background.setPreferredSize(new Dimension(180, 130));

        dialog.add(background);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
    }

    public void openDialog() {
        dialog.setVisible(true);
    }

    public String getNickName() {
        return nickName;
    }

    public boolean isConfirmed() {
        return confirmation;
    }
}
