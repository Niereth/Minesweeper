package minesweeper.controller;

import minesweeper.model.ModelField;

import java.util.Timer;
import java.util.TimerTask;

class GameTimer {
    private ModelField model;
    private Timer myTimer;
    private int secondsPassed;
    private boolean isRunning;

    GameTimer(ModelField m) {
        model = m;
    }

    void start() {
        if (isRunning) {
            myTimer.cancel();
        }
        myTimer = new Timer();
        long timeAtStart = System.currentTimeMillis();
        secondsPassed = 0;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                isRunning = true;
                long timeAtTick = System.currentTimeMillis();
                secondsPassed = (int) (timeAtTick - timeAtStart) / 1000;
                model.updateSecondsPassed(secondsPassed);
            }
        };
        myTimer.scheduleAtFixedRate(task, 0, 1000);
    }

    void stop() {
        if (isRunning) {
            myTimer.cancel();
            isRunning = false;
        }
    }
}