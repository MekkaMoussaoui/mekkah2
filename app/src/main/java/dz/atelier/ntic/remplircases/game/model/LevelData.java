package dz.atelier.ntic.remplircases.game.model;

import java.io.Serializable;

public class LevelData implements Serializable{
    private String level_title;
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LevelData (String level_title){
        this.level_title=level_title;

    }

    public String getLevel_title() {
        return level_title;
    }

    public void setLevel_title(String level_title) {
        this.level_title = level_title;
    }
}
