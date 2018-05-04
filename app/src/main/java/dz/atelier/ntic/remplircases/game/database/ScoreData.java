package dz.atelier.ntic.remplircases.game.database;



public class ScoreData {
    private int idScore;
    private int score;

    public ScoreData(int idScore, int score) {
        this.setIdScore( idScore );
        this.setScore( score );

    }

    public int getIdScore() {
        return idScore;
    }

    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
