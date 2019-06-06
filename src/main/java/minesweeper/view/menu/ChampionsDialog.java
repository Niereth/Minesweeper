package minesweeper.view.menu;

//import minesweeper.model.Champion;
//import minesweeper.model.Records;

import minesweeper.controller.Controller;
import minesweeper.model.Champion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChampionsDialog {
    private static Champion[] champions;
    private List<JLabel> records;
    private List<JLabel> nickNames;
    private JDialog dialog;
    private Controller controller;

    public ChampionsDialog(Controller controller) {
        this.controller = controller;
        initDialog();
    }

    private void initDialog() {
        JFrame frame;
        JPanel background;
        Box difficultyBox;
        Box recordsBox;
        Box nickNamesBox;

        frame = new JFrame();
        dialog = new JDialog(frame, "Чемпионы по категориям", true);

        JLabel difficultyBeginner = new JLabel("Новичок:");
        JLabel difficultyMedium = new JLabel("Любитель:");
        JLabel difficultyExpert = new JLabel("Профессионал:  ");

        JButton resetButton = new JButton("Сброс результатов");
        resetButton.setFocusable(false);
        resetButton.addActionListener(e -> {
            controller.resetResults();
            updateFields();
        });

        JButton okButton = new JButton("ОК");
        okButton.setFocusable(false);
        okButton.addActionListener(e -> dialog.setVisible(false));

        difficultyBox = new Box(BoxLayout.Y_AXIS);
        difficultyBox.add(difficultyBeginner);
        difficultyBox.add(difficultyMedium);
        difficultyBox.add(difficultyExpert);

        recordsBox = new Box(BoxLayout.Y_AXIS);
        nickNamesBox = new Box(BoxLayout.Y_AXIS);
        records = new ArrayList<>();
        nickNames = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            JLabel record = new JLabel();
            recordsBox.add(record);
            records.add(record);

            JLabel nickName= new JLabel();
            nickNamesBox.add(nickName);
            nickNames.add(nickName);
        }
        updateFields();

        background = new JPanel();
        background.setBorder(new EmptyBorder(5, 5, 5, 5));
        background.add(difficultyBox);
        background.add(recordsBox);
        background.add(nickNamesBox);
        background.add(resetButton);
        background.add(okButton);
        background.setPreferredSize(new Dimension(250, 100));

        dialog.add(background);
        dialog.pack();
        dialog.setResizable(false);
    }

    private void updateFields() {
        controller.loadRecords();
        for (int i = 0; i < records.size(); i++) {
            records.get(i).setText(champions[i].getRecord());
            nickNames.get(i).setText(champions[i].getNickName());
        }
    }

    public void openDialog(JMenuBar whereToOpen) {
        dialog.setLocationRelativeTo(whereToOpen);
        dialog.setVisible(true);
    }

    public static void setChampions(Champion[] champions) {
        ChampionsDialog.champions = champions;
    }
}
