package minesweeper.view.menu;

import minesweeper.controller.Controller;
import minesweeper.model.Difficulty;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMenu {
    private Controller controller;
    private JMenuBar menuBar;

    public GUIMenu(Controller controller) {
        this.controller = controller;
        initMenu();
    }

    private void initMenu() {
        menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("Файл");

        JMenuItem miStart = new JMenuItem("Новая игра");
        miStart.setActionCommand("NewGame");
        miStart.addActionListener(new MenuListeners());
        miStart.setAccelerator(KeyStroke.getKeyStroke("F2"));

        JMenuItem miBeginner = new JMenuItem("Новичок");
        miBeginner.setActionCommand("Beginner");
        miBeginner.addActionListener(new MenuListeners());

        JMenuItem miMedium = new JMenuItem("Любитель");
        miMedium.setActionCommand("Medium");
        miMedium.addActionListener(new MenuListeners());

        JMenuItem miExpert = new JMenuItem("Профессионал");
        miExpert.setActionCommand("Expert");
        miExpert.addActionListener(new MenuListeners());

        JMenuItem miCustom = new JMenuItem("Особые...");
        miCustom.setActionCommand("Custom");
        miCustom.addActionListener(new MenuListeners());

        JMenuItem miChampions = new JMenuItem("Чемпионы...");
        miChampions.setActionCommand("Champions");
        miChampions.addActionListener(new MenuListeners());

        JMenuItem miExit = new JMenuItem("Выход");
        miExit.setActionCommand("Exit");
        miExit.addActionListener(new MenuListeners());

        menuFile.add(miStart);
        menuFile.addSeparator();
        menuFile.add(miBeginner);
        menuFile.add(miMedium);
        menuFile.add(miExpert);
        menuFile.add(miCustom);
        menuFile.addSeparator();
        menuFile.add(miChampions);
        menuFile.addSeparator();
        menuFile.add(miExit);

        JMenu menuHelp = new JMenu("Справка");
        JMenuItem miRules = new JMenuItem("Правила игры");
        miRules.setActionCommand("Rules");
        miRules.addActionListener(new MenuListeners());

        menuHelp.add(miRules);

        menuBar.add(menuFile);
        menuBar.add(menuHelp);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    class MenuListeners implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            switch (e.getActionCommand()) {
                case "NewGame":
                    controller.restartGame();
                    break;
                case "Beginner":
                    controller.startNewGame(Difficulty.BEGINNER);
                    break;
                case "Medium":
                    controller.startNewGame(Difficulty.MEDIUM);
                    break;
                case "Expert":
                    controller.startNewGame(Difficulty.EXPERT);
                    break;
                case "Custom":
                    CustomSettingsDialog custom = new CustomSettingsDialog(controller);
                    custom.openDialog(menuBar);
                    break;
                case "Champions":
                    ChampionsDialog champions = new ChampionsDialog(controller);
                    champions.openDialog(menuBar);
                    break;
                case "Exit":
                    controller.clickMenuExit();
                    break;
                case "Rules":
                    RulesDialog rules = new RulesDialog();
                    rules.openDialog();
                    break;
                default:
            }
        }
    }
}
