package sample;

public class Result {
    private int resultID;
    private int exerciseID;
    private String username;
    private String resultScore;
    private String date;


    public Result(int resultID, int exerciseID, String username, String resultScore, String date) {
        this.resultID = resultID;
        this.exerciseID = exerciseID;
        this.username = username;
        this.resultScore = resultScore;
        this.date = date;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(int exerciseID) {
        this.exerciseID = exerciseID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResultScore() {
        return resultScore;
    }

    public void setResultScore(String resultScore) {
        this.resultScore = resultScore;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
