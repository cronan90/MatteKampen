package com.example.joakim.mattekampen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Result {
	private int correct, wrong, percentage;
	private Date date;

	Result(int correct, int wrong, Date date )
	{
		this.correct = correct;
		this.wrong = wrong;
		this.percentage = Math.round(100*(correct/(float)(correct+wrong)));;
		this.date = date;
	}

	public int getCorrect() {return correct;}
	public int getWrong() {return wrong;}
	public int getPercentage() {return percentage;}
	public Date getDate() {return date;}

	public String toString()
	{
		return "#Rätt: " + Integer.toString(this.correct)+ "\n" +
                "#Fel: " + Integer.toString(this.wrong) + "\n" +
                "Procent: " + Integer.toString(this.percentage) + "%\n" +
				"Datum: " + this.date.toString() + "\n" +
                "=============";
	}
}
