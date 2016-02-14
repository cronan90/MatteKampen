package com.example.joakim.mattekampen;

import java.util.Collections;
import java.util.List;

public class  Question {
	// A question has a questionLine followed by the correct answer and at least 3 dummy answers
	private String questionLine, correctAnswer;
    private List<String> dummies;
	
	Question(List<String> questionPaste)
	{
		this.questionLine = questionPaste.get(0);
		questionPaste.remove(0);
		this.correctAnswer = questionPaste.get(0);
		questionPaste.remove(0);
		this.dummies = questionPaste;
		Collections.shuffle(this.dummies);
	}

    public String getQuestionLine() {
        return questionLine;
    }

    public List<String> getDummies() {
        return dummies;
    }

    public String getCorrectAnswer(){ return this.correctAnswer;}
}