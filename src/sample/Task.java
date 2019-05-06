package sample;

public class Task {

    private int QuestionID;
    private String questionText;
    private String answer;

    public Task(int questionID, String questionText, String answer) {
        QuestionID = questionID;
        this.questionText = questionText;
        this.answer = answer;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
