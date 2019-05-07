package sample;

public class Result {
    private int resultID;
    private int exerciseID;
    private String username;

    public Result(int resultID, int exerciseID, String username) {
        this.resultID = resultID;
        this.exerciseID = exerciseID;
        this.username = username;
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


}
