package minesweeper.model;

import com.google.gson.reflect.TypeToken;
import minesweeper.json.JsonProvider;

import java.lang.reflect.Type;
import java.util.List;

class Records {
    private JsonProvider provider;
    private Type type;
    private Champion[] champions;
    private List<Observer> observers;
    private int championIndex;

    Records(List<Observer> observers) {
        provider = new JsonProvider();
        type = new TypeToken<Champion[]>() {}.getType();
        this.observers = observers;
    }

    void checkIsRecord(Difficulty difficulty, int secondsPassed) {
        String championCategory;
        switch (difficulty) {
            case BEGINNER:
                championIndex = 0;
                championCategory = "новичков";
                break;
            case MEDIUM:
                championIndex = 1;
                championCategory = "любителей";
                break;
            case EXPERT:
                championIndex = 2;
                championCategory = "профессионалов";
                break;
            default:
                notifyNoRecordSet();
                return;
        }
        loadRecords();
        if (secondsPassed < champions[championIndex].getSeconds()) {
            notifyNewRecordIsReadyToBeSaved(championCategory);
            return;
        }
        notifyNoRecordSet();
    }

    void loadRecords() {
        champions = (Champion[]) provider.loadJson(type);
        if (champions == null) {
            createDefaultRecords();
        }
        notifyRecordsUpdated(champions);
    }

    void createDefaultRecords() {
        Champion[] defaultChampions = {new Champion(), new Champion(), new Champion()};
        provider.saveJson(defaultChampions, type);
        notifyRecordsUpdated(defaultChampions);
    }

    void saveRecord(int secondsPassed, String nickName) {
        champions[championIndex] = new Champion(secondsPassed, nickName);
        provider.saveJson(champions, type);
        notifyNewRecordIsSaved();
    }

    private void notifyNoRecordSet() {
        for (Observer observer : observers) {
            observer.noRecordSet();
        }
    }

    private void notifyNewRecordIsReadyToBeSaved(String championCategory) {
        for (Observer observer : observers) {
            observer.newRecordIsReadyToBeSaved(championCategory);
        }
    }

    private void notifyNewRecordIsSaved() {
        for (Observer observer : observers) {
            observer.newRecordIsSaved();
        }
    }

    private void notifyRecordsUpdated(Champion[] champions) {
        for (Observer observer : observers) {
            observer.recordsUpdated(champions);
        }
    }
}