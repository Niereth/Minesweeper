package minesweeper;

import minesweeper.controller.Controller;
import minesweeper.model.Difficulty;
import minesweeper.model.ModelField;
import minesweeper.view.GUIBuild;

public class GameLauncher {

    public static void main(String[] args) {
        ModelField model = new ModelField();
        Controller controller = new Controller(model);
        GUIBuild view = new GUIBuild(controller);
        model.addObserver(view);
        view.gameLaunchedFirstTime();
        model.startNewGame(Difficulty.BEGINNER);
    }
}