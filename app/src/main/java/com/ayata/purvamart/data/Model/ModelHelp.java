package com.ayata.purvamart.data.Model;

public class ModelHelp {

    private String question;
    private String answer;
    private HelpType helpType;

    public enum HelpType {
        HELP_CLOSE, HELP_OPEN;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public HelpType getHelpType() {
        return helpType;
    }

    public void setHelpType(HelpType helpType) {
        this.helpType = helpType;
    }

    public ModelHelp(String question, String answer, HelpType helpType) {
        this.question = question;
        this.answer = answer;
        this.helpType = helpType;
    }

}
