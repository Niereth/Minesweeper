package minesweeper.model;

public class Champion {
    private int seconds;
    private String record;
    private String nickName;

    Champion() {
        seconds = 999;
        record = seconds + " сек.    ";
        nickName = "Аноним";
    }

    Champion(int seconds, String nickName) {
        this.seconds = seconds;
        this.record = seconds + " сек.    ";
        this.nickName = nickName;
    }

    int getSeconds() {
        return seconds;
    }

    public String getNickName() {
        return nickName;
    }

    public String getRecord() {
        return record;
    }
}
